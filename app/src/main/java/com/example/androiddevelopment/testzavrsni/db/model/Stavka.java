package com.example.androiddevelopment.testzavrsni.db.model;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = Stavka.TABLE_NAME_USERS)
public class Stavka {

    public static final String TABLE_NAME_USERS = "stavka";
    public static final String FIELD_NAME_ID     = "id";
    public static final String TABLE_STAVKA_NASLOV = "naslov";
    public static final String TABLE_STAVKA_OPIS = "opis";
    public static final String TABLE_STAVKA_NIVO = "nivo";
    public static final String TABLE_STAVKA_KOMENTAR = "komentar";
    public static final String TABLE_STAVKA_DATUM = "datum";
    public static final String TABLE_STAVKA_PIC = "pic";
    public static final String FIELD_NAME_USER = "user";

    @DatabaseField(columnName = FIELD_NAME_ID, generatedId = true)
    private int mId;

    @DatabaseField(columnName = TABLE_STAVKA_NASLOV)
    private String mNaslov;

    @DatabaseField(columnName = TABLE_STAVKA_OPIS)
    private String mOpis;

    @DatabaseField(columnName = TABLE_STAVKA_NIVO)
    private String mNivo;

    @DatabaseField(columnName = TABLE_STAVKA_KOMENTAR)
    private String mKomentar;

    @DatabaseField(columnName = TABLE_STAVKA_DATUM)
    private String mDatum;

    @DatabaseField(columnName = TABLE_STAVKA_PIC)
    private String mPic;

    @DatabaseField(columnName = FIELD_NAME_USER, foreign = true, foreignAutoRefresh = true)
    private Prijava mUser;

    public Stavka() {
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmNaslov() {
        return mNaslov;
    }

    public void setmNaslov(String mNaslov) {
        this.mNaslov = mNaslov;
    }

    public String getmOpis() {
        return mOpis;
    }

    public void setmOpis(String mOpis) {
        this.mOpis = mOpis;
    }

    public String getmNivo() {
        return mNivo;
    }

    public void setmNivo(String mNivo) {
        this.mNivo = mNivo;
    }

    public String getmKomentar() {
        return mKomentar;
    }

    public void setmKomentar(String mKomentar) {
        this.mKomentar = mKomentar;
    }

    public String getmDatum() {
        return mDatum;
    }

    public void setmDatum(String mDatum) {
        this.mDatum = mDatum;
    }

    public String getmPic() {
        return mPic;
    }

    public void setmPic(String mPic) {
        this.mPic = mPic;
    }

    public Prijava getmUser() {
        return mUser;
    }

    public void setmUser(Prijava mUser) {
        this.mUser = mUser;
    }

    @Override
    public String toString() {
        return mNaslov;
    }
}


