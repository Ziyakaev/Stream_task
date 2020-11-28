package tasks;

import common.Person;
import common.PersonService;
import common.Task;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/*
Задача 1
Метод на входе принимает List<Integer> id людей, ходит за ними в сервис
(он выдает несортированный Set<Person>, внутренняя работа сервиса неизвестна)
нужно их отсортировать в том же порядке, что и переданные id.
Оценить асимпотику работы
 */
public class Task1 implements Task {

	// !!! Редактируйте этот метод !!!
    private List<Person> findOrderedPersons(List<Integer> personIds) {
      Set<Person> persons = PersonService.findPersons(personIds);
      if (persons == null || persons.isEmpty()) {
        return Collections.emptyList();
      }

      Map<Integer, Person> idByPerson = new HashMap<>(persons.size());
      // O(N)
      persons.forEach(p -> idByPerson.put(p.getId(), p));
      //O(N)
      return personIds.stream()
              .map(id -> idByPerson.get(id))
              .collect(Collectors.toList()); // result 2 * O(N)
    }

	@Override
	public boolean check() {
		List<Integer> ids = List.of(1, 2, 3);

		return findOrderedPersons(ids).stream()
				.map(Person::getId)
				.collect(Collectors.toList())
				.equals(ids);
	}

}
