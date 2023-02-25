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

import org.jfin.date.util.ISDADateFormat;
import org.jfin.date.holiday.HolidayCalendarFactory;
import org.jfin.date.holiday.HolidayCalendar;
import org.jfin.date.BusinessDayConvention;

import java.util.Calendar;
import java.text.ParseException;

/**
 * Creates two dates which are weekends
 * and span a month end.
 * Adjust the two dates using:
 * Preceding
 * Following
 * Modified Preceding
 * Modified Floowing
 */
public class AdjustDate {
	public static void main(String[] args) throws ParseException {
		// Creaate two new dates for
		// Saturday 30 September and
		// Sunday 1 October
		Calendar date1 = ISDADateFormat.parse("2006/9/30");
		Calendar date2 = ISDADateFormat.parse("2006/10/1");

		// Use the default holiday calendar
		// factory to provide us with a
		// holiday calendar for weekends
		HolidayCalendar weekends = HolidayCalendarFactory.newInstance().getHolidayCalendar("WE");

		// Use the weekends holiday calendar
		// to print out date1 and date2
		// adjusted
		System.out.println(ISDADateFormat.format(date1)+" PRECEDING "+ISDADateFormat.format(weekends.adjust(date1, BusinessDayConvention.PRECEDING)));
		System.out.println(ISDADateFormat.format(date1)+" FOLLOWING "+ISDADateFormat.format(weekends.adjust(date1, BusinessDayConvention.FOLLOWING)));
		System.out.println(ISDADateFormat.format(date1)+" MODIFIED PRECEDING "+ISDADateFormat.format(weekends.adjust(date1, BusinessDayConvention.MODIFIED_PRECEDING)));
		System.out.println(ISDADateFormat.format(date1)+" MODIFIED FOLLOWING "+ISDADateFormat.format(weekends.adjust(date1, BusinessDayConvention.MODIFIED_FOLLOWING)));

		System.out.println(ISDADateFormat.format(date2)+" PRECEDING "+ISDADateFormat.format(weekends.adjust(date2, BusinessDayConvention.PRECEDING)));
		System.out.println(ISDADateFormat.format(date2)+" FOLLOWING "+ISDADateFormat.format(weekends.adjust(date2, BusinessDayConvention.FOLLOWING)));
		System.out.println(ISDADateFormat.format(date2)+" MODIFIED PRECEDING "+ISDADateFormat.format(weekends.adjust(date2, BusinessDayConvention.MODIFIED_PRECEDING)));
		System.out.println(ISDADateFormat.format(date2)+" MODIFIED FOLLOWING "+ISDADateFormat.format(weekends.adjust(date2, BusinessDayConvention.MODIFIED_FOLLOWING)));

//		Outputs:
//		2006/9/30 PRECEDING 2006/9/29
//		2006/9/30 FOLLOWING 2006/10/2
//		2006/9/30 MODIFIED PRECEDING 2006/9/29
//		2006/9/30 MODIFIED FOLLOWING 2006/9/29
//		2006/10/1 PRECEDING 2006/9/29
//		2006/10/1 FOLLOWING 2006/10/2
//		2006/10/1 MODIFIED PRECEDING 2006/10/2
//		2006/10/1 MODIFIED FOLLOWING 2006/10/2

	}
}
