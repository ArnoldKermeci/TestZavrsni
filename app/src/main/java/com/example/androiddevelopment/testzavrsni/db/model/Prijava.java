package com.example.androiddevelopment.testzavrsni.db.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Prijava.TABLE_NAME_USERS)
public class Prijava {

    public static final String TABLE_NAME_USERS = "prijava";
    public static final String FIELD_NAME_ID     = "id";
    public static final String TABLE_PRIJAVA_IME = "IME";
    public static final String TABLE_PRIJAVA_OPIS = "opis";
    public static final String TABLE_PRIJAVA_STATUS = "status";
    public static final String TABLE_PRIJAVA_DATUM = "datum";
    public static final String TABLE_PRIJAVA_STAVKA = "stavka";
    public static final String FIELD_NAME_USER  = "user";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = TABLE_PRIJAVA_IME)
    private String mIme;

    @DatabaseField(columnName = TABLE_PRIJAVA_OPIS)
    private String mOpis;

    @DatabaseField(columnName = TABLE_PRIJAVA_STATUS)
    private String mStatus;

    @DatabaseField(columnName = TABLE_PRIJAVA_DATUM)
    private String mDatum;

    @DatabaseField(columnName = FIELD_NAME_USER)
    private String mUser;

    @ForeignCollectionField(columnName = Prijava.TABLE_PRIJAVA_STAVKA, eager = true)
    private ForeignCollection<Stavka> stavkas;

    public Prijava() {
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmIme() {
        return mIme;
    }

    public void setmIme(String mIme) {
        this.mIme = mIme;
    }

    public ForeignCollection<Stavka> getStavkas() {
        return stavkas;
    }

    public void setStavkas(ForeignCollection<Stavka> stavkas) {
        this.stavkas = stavkas;
    }

    public String getmOpis() {
        return mOpis;
    }

    public void setmOpis(String mOpis) {
        this.mOpis = mOpis;
    }

    public String getmStatus() {
        return mStatus;
    }

    public void setmStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmDatum() {
        return mDatum;
    }

    public void setmDatum(String mDatum) {
        this.mDatum = mDatum;
    }

    public String getmUser() {
        return mUser;
    }

    public void setmUser(String mUser) {
        this.mUser = mUser;
    }

    @Override
    public String toString() {
        return mIme;
    }
}

