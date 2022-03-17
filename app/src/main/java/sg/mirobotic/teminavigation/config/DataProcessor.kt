package sg.mirobotic.teminavigation.config

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import sg.mirobotic.teminavigation.config.AppDatabase.Companion.TABLE_MAP_LOCATION
import sg.mirobotic.teminavigation.config.AppDatabase.Companion.TABLE_PATH
import sg.mirobotic.teminavigation.data.Path

class DataProcessor (context: Context) {

    private val database = AppDatabase(context)

    fun savePath(path: Path) {

        val values = ContentValues()
        values.put("name", path.name)

        val dbWrite = database.writableDatabase

        val id = dbWrite.insert(TABLE_PATH, null, values)

        saveLocations(dbWrite, id, path.locations)

        dbWrite.close()
    }

    private fun saveLocations(dbWrite: SQLiteDatabase, pathId: Long, locations: ArrayList<String>) {

        for (location in locations) {
            val values = ContentValues()
            values.put("path_id", pathId)
            values.put("location", location)

            dbWrite.insert(TABLE_MAP_LOCATION, null, values)

        }

    }

    fun getPaths(): ArrayList<Path> {

        val list = ArrayList<Path>()

        val query = "SELECT id, name FROM $TABLE_PATH"

        val dbRead = database.readableDatabase

        val cursor = dbRead.rawQuery(query, null)

        while (cursor.moveToNext()) {
            list.add(Path(cursor.getLong(0), cursor.getString(1)))
        }

        cursor.close()
        dbRead.close()
        return list
    }

    fun getLocationsForPath(path: Path): Path {
        val list = ArrayList<String>()

        val query = "SELECT location FROM $TABLE_MAP_LOCATION WHERE path_id = ${path.id}"
        val dbRead = database.readableDatabase
        val cursor = dbRead.rawQuery(query, null)

        while (cursor.moveToNext()) {
            list.add(cursor.getString(0))
        }

        path.locations = list
        cursor.close()
        dbRead.close()
        return path
    }

    fun deletePath(path: Path) {
        val dbWrite = database.writableDatabase
        dbWrite.execSQL("DELETE FROM $TABLE_PATH WHERE id = ${path.id}")
        dbWrite.execSQL("DELETE FROM $TABLE_MAP_LOCATION WHERE path_id = ${path.id}")
        dbWrite.close()
    }

    fun updatePath(path: Path) {
        val dbWrite = database.writableDatabase
        dbWrite.execSQL("DELETE FROM $TABLE_MAP_LOCATION WHERE path_id = ${path.id}")
        saveLocations(dbWrite, path.id, path.locations)

    }

}