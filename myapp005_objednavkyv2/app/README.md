# Přehled možností uložení objednávek

Možnosti uložení závisí na tom, zda mají data zůstat pouze v telefonu uživatele, nebo se odesílat na vzdálený server.

### 1. Lokální uložení (v zařízení)
* **Room (SQLite):** Oficiální standard pro Android (Jetpack). Ideální pro ukládání historie objednávek přímo do zařízení. Funguje plně offline, ale po odinstalaci aplikace se data smažou.
* **Preferences DataStore:** Vhodné pouze pro uložení rozpracované objednávky nebo nastavení, ne pro kompletní historii.

### 2. Cloudové uložení (na serveru)
* **Firebase Cloud Firestore:** Nejrychlejší cesta k uložení do cloudu bez nutnosti psát vlastní server. Má vestavěný offline režim a vývojářský tarif zdarma.
* **REST API + Vlastní databáze (PostgreSQL/MySQL):** Standard pro produkční aplikace. Vyžaduje tvorbu vlastního backendu (Node.js, C#, Python), ale dává plnou kontrolu nad daty a obchodní logikou.

---

### Doporučený výběr
* **Nejjednodušší propojení s cloudem:** Firebase Cloud Firestore
* **Uložení čistě do telefonu:** Room Persistence Library
* **Kompletní komerční systém:** REST API (Retrofit) + Vlastní server