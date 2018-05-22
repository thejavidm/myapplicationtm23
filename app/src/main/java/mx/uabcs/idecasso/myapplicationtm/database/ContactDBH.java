package mx.uabcs.idecasso.myapplicationtm.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import mx.uabcs.idecasso.myapplicationtm.models.Contact;
import mx.uabcs.idecasso.myapplicationtm.models.Phone;
import nl.qbusict.cupboard.CupboardBuilder;
import nl.qbusict.cupboard.CupboardFactory;

import static mx.uabcs.idecasso.myapplicationtm.utils.Constants.*;
import static nl.qbusict.cupboard.CupboardFactory.cupboard;


public class ContactDBH extends SQLiteOpenHelper {
    private static ContactDBH sInstance;
    public static final String DATABASE_NAME = "contacts.db";
    public static final int DATABASE_VERSION = 1;

    private ContactDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized ContactDBH getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new ContactDBH(context);
        }
        return sInstance;
    }

    static {
        CupboardFactory.setCupboard(new CupboardBuilder().useAnnotations().build());
        cupboard().register(Contact.class);
        cupboard().register(Phone.class);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        cupboard().withDatabase(db).createTables();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        cupboard().withDatabase(db).upgradeTables();
    }

    public static void addOrUpdate(Context context, Contact contact) {
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        long id = cupboard().withDatabase(db).put(contact);
        for (Phone phone : contact.getPhones()) {
            phone._idContact = id;
            addOrUpdate(context, phone);
        }
    }

    public static void addOrUpdate(Context context, Phone phone) {
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        cupboard().withDatabase(db).put(phone);
    }

    public static void delete(Context context, Contact contact) {
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        for (Phone phone : contact.getPhones()) {
            delete(context, phone);
        }
        cupboard().withDatabase(db).delete(contact);
    }

    public static void delete(Context context, Phone phone) {
        SQLiteDatabase db = getInstance(context).getWritableDatabase();
        cupboard().withDatabase(db).delete(phone);
    }

    public static Contact getContact(Context context, Long id) {
        SQLiteDatabase db = getInstance(context).getReadableDatabase();
        Contact contact = cupboard().withDatabase(db).get(Contact.class, id);
        List<Phone> phones = cupboard().withDatabase(db)
                .query(Phone.class)
                .withSelection("_idContact = ?", EMPTY_STRING + id)
                .list();
        contact.setPhones(phones);
        return contact;
    }

    public static Phone getPhone(Context context, Long id) {
        SQLiteDatabase db = getInstance(context).getReadableDatabase();
        return cupboard().withDatabase(db).get(Phone.class, id);
    }

    public static List<Contact> getContacts(Context context) {
        SQLiteDatabase db = getInstance(context).getReadableDatabase();
        List<Contact> contacts = cupboard().withDatabase(db)
                .query(Contact.class)
                .list();
        for (Contact contact : contacts) {
            List<Phone> phones = cupboard().withDatabase(db)
                    .query(Phone.class)
                    .withSelection("_idContact = ?", EMPTY_STRING + contact._id)
                    .list();
            contact.setPhones(phones);
        }
        return contacts;
    }

    public static List<Phone> getPhones(Context context) {
        SQLiteDatabase db = getInstance(context).getReadableDatabase();
        return cupboard().withDatabase(db)
                .query(Phone.class)
                .list();
    }
}

