package ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import time.MonthView;
import time.WeekView;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

import static data.exceptions.TaskManagerExceptionTest.DATE_TIME_FORMATTER;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WeekViewTest {

    private WeekView weekView;

    private MonthView monthView;

    @BeforeEach
    public void setUp() {
        LocalDate startOfWeek = LocalDate.of(2024, 3, 10); // March 10, 2024
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        weekView = new WeekView(startOfWeek, dateFormatter);
        monthView = new MonthView(startOfWeek, dateFormatter);
    }

    @Test
    public void testNextWeek() {
        LocalDate initialStartOfWeek = weekView.getStartOfWeek();
        weekView.next();
        LocalDate newStartOfWeek = initialStartOfWeek.plusWeeks(1);
        assertEquals(newStartOfWeek, weekView.getStartOfWeek());
    }

    @Test
    public void testPreviousWeek() {
        LocalDate initialStartOfWeek = weekView.getStartOfWeek();
        weekView.previous();
        LocalDate newStartOfWeek = initialStartOfWeek.minusWeeks(1);
        assertEquals(newStartOfWeek, weekView.getStartOfWeek());
    }

    @Test
    public void testNextMonth() {
        LocalDate initialStartOfWeek = monthView.getStartOfMonth();
        monthView.next();
        LocalDate newStartOfWeek = initialStartOfWeek.plusMonths(1).withDayOfMonth(1);
        assertEquals(newStartOfWeek, monthView.getStartOfMonth());
    }

    @Test
    public void testPreviousMonth() {
        LocalDate initialStartOfWeek = monthView.getStartOfMonth();
        monthView.previous();
        LocalDate newStartOfWeek = initialStartOfWeek.minusMonths(1).withDayOfMonth(1);
        assertEquals(newStartOfWeek, monthView.getStartOfMonth());
    }

    @Test
    void testWeekViewAtYearEnd() {
        LocalDate endOfYear = LocalDate.of(2023, 12, 31);

        weekView = new WeekView(endOfYear, DATE_TIME_FORMATTER);
        weekView.next();
        // The expected start of the next week should be the next Sunday, which is January 7, 2024
        LocalDate expectedStartOfNextWeek = LocalDate.of(2024, 1, 7);
        assertEquals(expectedStartOfNextWeek, weekView.getStartOfWeek());
    }

    @Test
    void testWeekViewAtYearStart() {
        LocalDate startOfYear = LocalDate.of(2024, 1, 1);
        weekView = new WeekView(startOfYear, DATE_TIME_FORMATTER);
        weekView.previous();
        LocalDate endOfPreviousYear = LocalDate.of(2023, 12, 31)
                .with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        assertEquals(endOfPreviousYear, weekView.getStartOfWeek());
    }

    @Test
    void testWeekViewDuringLeapYear() {
        LocalDate leapYearDate = LocalDate.of(2024, 2, 29);
        weekView = new WeekView(leapYearDate, DATE_TIME_FORMATTER);

        LocalDate expectedStartOfWeek = leapYearDate;
        assertEquals(expectedStartOfWeek, weekView.getStartOfWeek());
    }


    @Test
    void testWeekViewDayOfWeekConsistency() {
        LocalDate date = LocalDate.of(2023, 3, 10); // This is a Friday
        weekView = new WeekView(date, DATE_TIME_FORMATTER);

        LocalDate expectedStartOfWeek = date;
        weekView.next();

        LocalDate expectedNewDate = expectedStartOfWeek.plusWeeks(1);
        assertEquals(expectedNewDate, weekView.getStartOfWeek());
    }

}

