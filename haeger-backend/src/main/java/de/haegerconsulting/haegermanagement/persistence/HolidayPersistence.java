package de.haegerconsulting.haegermanagement.persistence;

import de.haegerconsulting.haegermanagement.utils.workDayCalculator.holiday.HolidayJPA;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayPersistence extends CrudRepository<HolidayJPA, Integer> {
}
