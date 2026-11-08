# Dry Realms (Forge 1.20.1 + TerraBlender)

## Сборка .jar через GitHub (без установки Java)
1. Зарегистрируйся на github.com, создай новый репозиторий (New repository, Public).
2. Нажми "uploading an existing file" и перетащи ВНУТРЬ всё содержимое папки dryrealms
   (включая скрытую папку .github). Нажми Commit changes.
3. Вкладка Actions -> "Build mod" -> Run workflow. Подожди 5-10 минут.
4. Открой завершённый запуск, внизу Artifacts -> dryrealms-jar. Внутри будет .jar мода.

## Установка в игру
Нужны Minecraft 1.20.1, Forge 47.x и мод TerraBlender (Forge, 1.20.1, версия 3.0.1.x).
Оба .jar (мод и TerraBlender) положи в папку mods.

## Команды
- /locatebiome dryrealms:scorched_lake (также desert_lake, flooded_valley, flooded_desert)
- /locate structure dryrealms:flooded_village_plains (или _desert)
- /locate structure dryrealms:flooded_ruins_valley (или _desert)

## Тестовый режим
1. Запусти игру один раз, чтобы создался config/dryrealms-common.toml, закрой.
2. Поставь в нём testMode = true.
3. Создай НОВЫЙ мир, сразу выйди, скопируй папку test_datapack в saves/<мир>/datapacks/ и зайди снова.
4. Телепорт к биому: /dryrealms tp <scorched_lake|desert_lake|flooded_valley|flooded_desert>
Для обычной игры верни testMode = false и убери test_datapack.
