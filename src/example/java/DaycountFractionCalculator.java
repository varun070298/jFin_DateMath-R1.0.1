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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.jfin.date.BusinessDayConvention;
import org.jfin.date.daycount.DaycountCalculator;
import org.jfin.date.daycount.DaycountCalculatorFactory;
import org.jfin.date.holiday.HolidayCalendar;
import org.jfin.date.holiday.HolidayCalendarFactory;
import org.jfin.date.util.ISDADateFormat;

public class DaycountFractionCalculator
{

	@SuppressWarnings("deprecation")
	public static void main(String[] args)
	{
		Calendar date1 = null;
		Calendar date2 = null;
		HolidayCalendar holidayCalendar = null;
		DaycountCalculator daycountBasis = null;

		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		try
		{
			Date d1 = df.parse(args[0]);
			date1 = new GregorianCalendar();
			date1.set(Calendar.YEAR, d1.getYear() + 1900);
			date1.set(Calendar.MONTH, d1.getMonth());
			date1.set(Calendar.DAY_OF_MONTH, d1.getDate());

			Date d2 = df.parse(args[1]);
			date2 = new GregorianCalendar();
			date2.set(Calendar.YEAR, d2.getYear() + 1900);
			date2.set(Calendar.MONTH, d2.getMonth());
			date2.set(Calendar.DAY_OF_MONTH, d2.getDate());

			holidayCalendar = HolidayCalendarFactory.newInstance()
					.getHolidayCalendar(args[2]);

			if (args[3].equals("ACTACT"))
			{
				daycountBasis = DaycountCalculatorFactory.newInstance()
						.getISDAActualActual();
			} else if (args[3].equals("HIST"))
			{
				daycountBasis = DaycountCalculatorFactory.newInstance()
						.getISMAActualActual();
			} else if (args[3].equals("AFB"))
			{
				daycountBasis = DaycountCalculatorFactory.newInstance()
						.getAFBActualActual();
			} else if (args[3].equals("30360"))
			{
				daycountBasis = DaycountCalculatorFactory.newInstance()
						.getUS30360();
			} else if (args[3].equals("30E360"))
			{
				daycountBasis = DaycountCalculatorFactory.newInstance()
						.getEU30360();
			} else if (args[3].equals("ACT360"))
			{
				daycountBasis = DaycountCalculatorFactory.newInstance()
						.getActual360();
			} else if (args[3].equals("ACT365"))
			{
				daycountBasis = DaycountCalculatorFactory.newInstance()
						.getActual365Fixed();
			} else
			{
				throw new Exception("Couldn't find day count calculator.");
			}

		} catch (Exception e)
		{
			System.out.println("Cannot parse parameters. Use syntax:");
			System.out
					.println("  DaycountFractionCalculator.sh startDate(YYYY/MM/DD) endDate(YYYY/MM/DD) holidayCalendar daycountBasis");
			System.out.println();
			System.out
					.println("Unless you have loaded your own HolidayCalendarProvider, use holidayCalendar=WE for the weekend calendar");
			System.out
					.println("daycountBasis can be one of the FOLLOWING values (case sensitive):");

			System.out.println("\tACTACT");
			System.out.println("\tHIST");
			System.out.println("\tAFB");
			System.out.println("\t30360");
			System.out.println("\t30E360");
			System.out.println("\tACT360");
			System.out.println("\tACT365");

			System.exit(-1);
		}

		try
		{
			Calendar date1Adj = holidayCalendar.adjust(date1,
					BusinessDayConvention.MODIFIED_FOLLOWING);
			Calendar date2Adj = holidayCalendar.adjust(date2,
					BusinessDayConvention.MODIFIED_FOLLOWING);

			double daycountFraction = daycountBasis.calculateDaycountFraction(
					date1Adj, date2Adj);

			System.out.println("Unadjusted start:  "
					+ ISDADateFormat.format(date1));
			System.out.println("Unadjusted end:    "
					+ ISDADateFormat.format(date2));
			System.out.println("Holiday calendar:  "
					+ holidayCalendar.getClass().getCanonicalName());
			System.out.println("Adjusted start:    "
					+ ISDADateFormat.format(date1Adj));
			System.out.println("Adjusted end:      "
					+ ISDADateFormat.format(date2Adj));
			System.out.println("Daycount basis:    "
					+ DaycountCalculator.class.getSimpleName());
			System.out.println("Daycount fraction: " + daycountFraction);
			System.exit(0);
		} catch (Exception e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
