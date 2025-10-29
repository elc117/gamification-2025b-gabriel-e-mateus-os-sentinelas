import initSqlJs from "./sqlite3.js";

export let db;

export async function initDatabase() {
    const SQL = await initSqlJs({
        locateFile: file => `sqlite3.wasm`
    });
    db = new SQL.Database();
    console.log("SQLite WASM inicializado!");
}

export function execSQL(sql) {
    try {
        db.run(sql);
    } catch (e) {
        console.error("Erro SQL:", e);
    }
}
