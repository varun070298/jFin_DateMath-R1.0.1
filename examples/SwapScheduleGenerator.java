/*
 * <p> <b>jFin, open source derivatives trade processing</b> </p>
 *
 * <p> Copyright (C) 2005, 2006, 2007 Morgan Brown Consultancy Ltd. </p>
 *
 * <p> This file is part of jFin. </p>
 *
 * <p> jFin is free software; you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later
 * version. </p>
 *
 * <p> jFin is distributed in the hope that it will be useful, but <b>WITHOUT
 * ANY WARRANTY</b>; without even the implied warranty of <b>MERCHANTABILITY</b>
 * or <b>FITNESS FOR A PARTICULAR PURPOSE</b>. See the GNU General Public
 * License for more details. </p>
 *
 * <p> You should have received a copy of the GNU General Public License along
 * with jFin; if not, write to the Free Software Foundation, Inc., 51 Franklin
 * St, Fifth Floor, Boston, MA 02110-1301 USA. </p>
 */

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import org.jfin.date.BusinessDayConvention;
import org.jfin.date.Frequency;
import org.jfin.date.Period;
import org.jfin.date.ScheduleException;
import org.jfin.date.ScheduleGenerator;
import org.jfin.date.StubType;
import org.jfin.date.daycount.DaycountCalculator;
import org.jfin.date.daycount.DaycountCalculatorFactory;
import org.jfin.date.daycount.DaycountException;
import org.jfin.date.holiday.HolidayCalendar;
import org.jfin.date.holiday.HolidayCalendarException;
import org.jfin.date.holiday.HolidayCalendarFactory;
import org.jfin.date.util.ISDADateFormat;

public class SwapScheduleGenerator
{

	private static Logger logger = Logger
			.getLogger("org.jfin.date.example.SwapScheduleGenerator");

	public static void main(String[] args) throws ScheduleException,
			HolidayCalendarException, DaycountException
	{
		String currency = "EUR";
		String index = "LIBOR";
		double notional = 1000000d;
		int fixingBusinessDayOffset = 0;
		Frequency frequency = Frequency.QUARTERLY;
		double fixedRate = 0.04;
		int maturityYears = 10;

		logger.info("Setting up the number formats for display");

		NumberFormat currencyFormat = new DecimalFormat();
		currencyFormat.setGroupingUsed(true);
		currencyFormat.setMaximumFractionDigits(2);

		NumberFormat floatFormat = new DecimalFormat();
		floatFormat.setMaximumFractionDigits(6);

		logger.info("Infer the fixing offset from the currency and index");

		if (currency.equals("GBP") && index.equals("LIBOR"))
		{
			logger.info("LIBOR GBP, fixing offset is 0 business days");
			fixingBusinessDayOffset = 0;
		} else
		{
			logger.info("Fixing offset is -2 business days");
			fixingBusinessDayOffset = -2;
		}

		logger.info("Get a weekend holiday calendar");
		HolidayCalendar holidayCalendar = HolidayCalendarFactory.newInstance()
				.getHolidayCalendar("WE");

		logger.info("Make the startDate today");
		Calendar startDate = new GregorianCalendar();

		logger.info("Adjust the start date two business days in the future");
		startDate = holidayCalendar.advanceBusinessDays(startDate, 2);

		logger.info("Make end date the start date + " + maturityYears + "Y");
		Calendar endDate = (Calendar) startDate.clone();
		endDate.add(Calendar.YEAR, maturityYears);

		logger
				.info("Generate a 3 MONTHLY schedule between the start and end date");
		List schedule = ScheduleGenerator.generateSchedule(startDate, endDate,
				frequency, StubType.SHORT_FIRST);

		logger.info("Set up the business day convention for the periods");
		BusinessDayConvention periodBusinessDayConvention = BusinessDayConvention.FOLLOWING;

		logger.info("Create a daycount calculator for ISDA Actual/Actual");
		DaycountCalculator daycountCalculator = DaycountCalculatorFactory
				.newInstance().getISDAActualActual();

		logger.info("Create Iterator to step through and print the schedule");
		Iterator it = schedule.iterator();

		logger.info("Step through the periods");

		System.out
				.println("Unadjusted period     | Adjusted period       | Fixing Date| Fix Amt      | Float Amt");
		System.out
				.println("----------------------+-----------------------+------------+--------------+-----------------");

		while (it.hasNext())
		{
			Period period = (Period) it.next();

			logger.info("Create an adjusted period");
			Period adjustedPeriod = holidayCalendar.adjust(period,
					periodBusinessDayConvention);

			logger.info("Calculate fixing date");
			Calendar fixingDate = holidayCalendar.advanceBusinessDays(
					(Calendar) adjustedPeriod.getStartCalendar().clone(),
					fixingBusinessDayOffset);

			logger
					.info("Calculate the daycount fraction for the adjust period");
			double daycountFraction = daycountCalculator
					.calculateDaycountFraction(adjustedPeriod);

			double fixedAmount = notional * daycountFraction * fixedRate;

			System.out.print(ISDADateFormat.formatFixedLength(period
					.getStartCalendar()));
			System.out.print("-");
			System.out.print(ISDADateFormat.formatFixedLength(period
					.getEndCalendar()));
			System.out.print(" | ");
			System.out.print(ISDADateFormat.formatFixedLength(adjustedPeriod
					.getStartCalendar()));
			System.out.print("-");
			System.out.print(ISDADateFormat.formatFixedLength(adjustedPeriod
					.getEndCalendar()));
			System.out.print(" | ");
			System.out.print(ISDADateFormat.formatFixedLength(fixingDate));
			System.out.print(" | ");
			String fixedAmountString = currencyFormat.format(fixedAmount);
			for (int i = fixedAmountString.length(); i < 12; i++)
				System.out.print(" ");
			System.out.print(fixedAmountString);
			System.out.print(" | ");
			System.out.print(index + ":" + currency + ":"
					+ frequency.getTenorDescriptor() + "*"
					+ floatFormat.format(daycountFraction));
			System.out.println();
		}
		System.exit(0);
	}

}
