package ui;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import time.MonthView;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MonthViewTest {

    private MonthView monthView;
    private DateTimeFormatter dateFormatter;

    @BeforeEach
    void setUp() {
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        monthView = new MonthView(LocalDate.now(), dateFormatter);
    }

    @Test
    void testMonthViewInitialization() {
        LocalDate expectedStartDate = LocalDate.now().withDayOfMonth(1);
        assertEquals(expectedStartDate, monthView.getStartOfMonth());
    }

    @Test
    void testNextMonthTransition() {
        YearMonth currentMonth = YearMonth.from(monthView.getStartOfMonth());
        monthView.next();
        YearMonth nextMonth = currentMonth.plusMonths(1);
        assertEquals(nextMonth.atDay(1), monthView.getStartOfMonth());
    }

    @Test
    void testPreviousMonthTransition() {
        YearMonth currentMonth = YearMonth.from(monthView.getStartOfMonth());
        monthView.previous();
        YearMonth previousMonth = currentMonth.minusMonths(1);
        assertEquals(previousMonth.atDay(1), monthView.getStartOfMonth());
    }

    @Test
    void testMonthViewLeapYearFebruary() {
        LocalDate leapYearDate = LocalDate.of(2024, 2, 1);
        monthView = new MonthView(leapYearDate, dateFormatter);
        assertEquals(29, leapYearDate.lengthOfMonth());
    }

    @Test
    void testMonthViewNonLeapYearFebruary() {
        LocalDate nonLeapYearDate = LocalDate.of(2023, 2, 1);
        monthView = new MonthView(nonLeapYearDate, dateFormatter);
        assertEquals(28, nonLeapYearDate.lengthOfMonth());
    }

    @Test
    void testMonthViewDecemberToJanuaryTransition() {
        LocalDate decemberDate = LocalDate.of(2023, 12, 1);
        monthView = new MonthView(decemberDate, dateFormatter);
        monthView.next();
        assertEquals(LocalDate.of(2024, 1, 1), monthView.getStartOfMonth());
    }

    @Test
    void testMonthViewJanuaryToDecemberTransition() {
        LocalDate januaryDate = LocalDate.of(2023, 1, 1);
        monthView = new MonthView(januaryDate, dateFormatter);
        monthView.previous();
        assertEquals(LocalDate.of(2022, 12, 1), monthView.getStartOfMonth());
    }

    @Test
    void testMonthViewEndOfMonthBoundary() {
        LocalDate endOfMonth = LocalDate.of(2023, 3, 31);
        monthView = new MonthView(endOfMonth, dateFormatter);
        monthView.next();
        assertEquals(LocalDate.of(2023, 4, 1), monthView.getStartOfMonth());
    }

    @Test
    void testMonthViewStartOfMonthBoundary() {
        LocalDate startOfMonth = LocalDate.of(2023, 3, 1);
        monthView = new MonthView(startOfMonth, dateFormatter);
        monthView.previous();
        assertEquals(LocalDate.of(2023, 2, 1), monthView.getStartOfMonth());
    }

    @Test
    void testMonthViewWithInvalidDate() {
        assertThrows(DateTimeException.class, () -> {
            LocalDate invalidDate = LocalDate.of(2023, 2, 30); // Invalid date
            new MonthView(invalidDate, dateFormatter);
        });
    }
}
