package sg.mirobotic.teminavigation.config

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class AppDatabase (context: Context): SQLiteOpenHelper(context, dbName, null, dbVersion) {

    companion object {
        private const val TAG = "AppDB"
        private const val dbName = "temi"
        private const val dbVersion = 1
        const val TABLE_PATH = "path"
        const val TABLE_MAP_LOCATION = "path_location"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val tablePath =  "CREATE TABLE '$TABLE_PATH'  (\n" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "'name' TEXT\n" +
                ");"

        val tableMapLocation =  "CREATE TABLE '$TABLE_MAP_LOCATION'  (\n" +
                "'id' INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "'path_id' INTEGER,\n" +
                "'location' TEXT\n" +
                ");"

        db.execSQL(tablePath)
        db.execSQL(tableMapLocation)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

}