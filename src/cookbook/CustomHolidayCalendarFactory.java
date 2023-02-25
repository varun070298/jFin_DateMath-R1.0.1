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
import org.jfin.date.holiday.HolidayCalendarException;
import org.jfin.date.Period;

/**
 * Created by IntelliJ IDEA.
 * User: dmb
 * Date: Oct 27, 2006
 * Time: 8:05:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class CustomHolidayCalendarFactory extends HolidayCalendarFactory {
	public HolidayCalendar getHolidayCalendar(String locale) {
		return getHolidayCalendar(locale, Period.class);
	}

	public <T extends Period> HolidayCalendar<T> getHolidayCalendar(String locale, Class<T> c) {
		// This is where you determine, based upon the locale string
		// what holiday calendar to return, and how it is configured
		if("TUES".equals(locale)) {
			return new TuesdayHolidayCalendar();
		} else {
			throw new HolidayCalendarException("We don't support the locale \""+locale+"\", try \"TUES\"");
		}
	}

	public String[] getAvailableLocales() {
		// Return the names of the locales
		// provided (here just TUES for the
		// tuesday holiday calendar
		return new String[]{"TUES"};
	}
}
