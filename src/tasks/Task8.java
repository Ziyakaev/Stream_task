package tasks;

import common.Person;
import common.Task;

import java.time.Instant;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
А теперь о горьком
Всем придется читать код
А некоторым придется читать код, написанный мною
Сочувствую им
Спасите будущих жертв, и исправьте здесь все, что вам не по душе!
P.S. функции тут разные и рабочие (наверное), но вот их понятность и эффективность страдает (аж пришлось писать комменты)
P.P.S Здесь ваши правки желательно прокомментировать (можно на гитхабе в пулл реквесте)
 */
public class Task8 implements Task {

	private long count;

	//Не хотим выдывать апи нашу фальшивую персону, поэтому конвертим начиная со второй
	public List<String> getNames(List<Person> persons) {
		return persons.stream().skip(1)
		  .map(Person::getFirstName)
		  .collect(Collectors.toList());
	}

	//ну и различные имена тоже хочется
	public Set<String> getDifferentNames(List<Person> persons) {
		return new HashSet<>(getNames(persons));
	}

	//Для фронтов выдадим полное имя, а то сами не могут
	public String convertPersonToString(Person person) {
		return Stream.of(person.getFirstName(), person.getSecondName(), person.getMiddleName())
		  .filter(Objects::nonNull)
		  .collect(Collectors.joining(" "));
	}

	// словарь id персоны -> ее имя
	public Map<Integer, String> getPersonNames(Collection<Person> persons) {
		return persons.stream()
		  .collect(Collectors.toMap(Person::getId, Person::getFirstName, (oldKey, newKey) -> oldKey));
	}

	// есть ли совпадающие в двух коллекциях персоны?
	public boolean hasSamePersons(Collection<Person> persons1, Collection<Person> persons2) {
		return persons1.stream().anyMatch(v -> persons2.contains((v)));
	}

	//...
	public long countEven(Stream<Integer> numbers) {
		return numbers.filter(num -> num % 2 == 0)
		  .count();
	}

	@Override
	public boolean check() {
		Person person = new Person(1, "Oleg", "Tinkoff", "Urevich", Instant.now());
		List<Person> persons = List.of(person,
		  new Person(2, "German", "Gref", "Oscarovich", Instant.now()),
		  new Person(3, "Boris", "Dobrodeev", "Olegovich", Instant.now()),
		  new Person(4, "Oleg", "Dublicate", "Name", Instant.now()),
		  new Person(5, "Mihail", "Zykov", "None", Instant.now())
		);

		Set<String> differentNames = getDifferentNames(persons);
		Map<Integer, String> idPersonName = getPersonNames(persons);
		List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7);
		long countEven = countEven(numbers.stream());
		String fullName = convertPersonToString(person);
		System.out.println("Слабо дойти до сюда и исправить Fail этой таски?");
		boolean hasSamePerson = hasSamePersons(persons, List.of(person));

		return Set.of("Oleg", "German", "Boris", "Mihail").equals(differentNames)
		  && ("Oleg Tinkoff Urevich").equals(fullName)
		  && Map.of(1, "Oleg", 2, "German", 3, "Boris", 4, "Oleg", 5, "Mihail")
		  .equals(idPersonName)
		  && (countEven == 3)
		  && hasSamePerson;
	}
}
