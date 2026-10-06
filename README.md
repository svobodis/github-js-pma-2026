# Porovnání aktualizace uživatelského rozhraní

### 1. XML + Kotlin (Imperativní přístup)
* **Princip:** Uživatelské rozhraní je definováno staticky v XML souboru. V Kotlin kódu se pomocí funkce `findViewById` ručně vyhledají odkazované instance prvků (`TextView`, `Button`).
* **Aktualizace UI:** Kód přímo určuje *jak* a *kdy* změnit vlastnosti jednotlivých prvků. Při změně textu se volá imperativní příkaz `tvDice.text = ...` a při změně stavu tlačítka `btnRoll.isEnabled = ...`.

### 2. Jetpack Compose (Deklarativní přístup)
* **Princip:** Rozhraní se popisuje pomocí Kotlin funkcí s anotací `@Composable`. Nepoužívají se žádné XML soubory ani příkazy typu `findViewById`.
* **Aktualizace UI:** Rozhraní reaguje na změnu **stavu** (`State`). Stav je reprezentován proměnnými `diceValue` a `isRolling`. Jakmile se hodnota v proměnné změní, Compose automaticky vyvolá tzv. **rekompozici** (recomposition) – znovu vykreslí pouze ty části UI, které na tomto stavu závisí. Neupravujeme prvky přímo, pouze měníme data a framework se postará o překreslení.