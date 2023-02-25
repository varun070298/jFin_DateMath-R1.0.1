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

import org.jfin.date.holiday.HolidayCalendarFactory;
import org.jfin.date.holiday.HolidayCalendar;
import org.jfin.date.util.ISDADateFormat;
import org.jfin.date.BusinessDayConvention;

import java.util.Calendar;
import java.text.ParseException;

/**
 * Created by IntelliJ IDEA.
 * User: dmb
 * Date: Oct 27, 2006
 * Time: 8:12:36 AM
 * To change this template use File | Settings | File Templates.
 */
public class CustomHolidayCalendar {
	public static void main(String[] args) throws ParseException {
		// Get our custom holiday
		// calendar factory
		HolidayCalendarFactory factory = HolidayCalendarFactory.newInstance("CustomHolidayCalendarFactory");

		// Get the Tuesday holiday
		// calendar
		HolidayCalendar tuesdayCalendar = factory.getHolidayCalendar("TUES");

		// Create a couple of dates
		Calendar monday = ISDADateFormat.parse("2006/10/9");
		Calendar tuesday = ISDADateFormat.parse("2006/10/10");

		// Adjust the dates using
		// our custom calendar
		System.out.println("Adjusting monday "+ISDADateFormat.format(monday)+" preceding "+ISDADateFormat.format(tuesdayCalendar.adjust(monday, BusinessDayConvention.PRECEDING)));
		System.out.println("Adjusting tuesday "+ISDADateFormat.format(tuesday)+" preceding "+ISDADateFormat.format(tuesdayCalendar.adjust(tuesday, BusinessDayConvention.PRECEDING)));

	}
}
