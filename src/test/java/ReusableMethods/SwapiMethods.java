package ReusableMethods;

import Models.CharacterModel;

public class SwapiMethods {

    public static boolean ComparePeople(CharacterModel comparator, CharacterModel subject) {
        return comparator.name.equals(subject.name)
                && comparator.gender.equals(subject.gender)
                && comparator.eye_color.equals(subject.eye_color)
                && comparator.skin_color.equals(subject.skin_color)
                && comparator.birth_year.equals(subject.birth_year)
                && comparator.hair_color.equals(subject.hair_color)
                && comparator.height.equals(subject.height)
                && comparator.mass.equals(subject.mass);
    }

    public static boolean ValidateCharacterClass(CharacterModel character) {
        return (
                character.name != null
                );
    }
}
