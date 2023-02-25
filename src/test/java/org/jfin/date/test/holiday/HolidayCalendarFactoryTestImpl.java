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

package org.jfin.date.test.holiday;

import org.jfin.date.holiday.HolidayCalendar;
import org.jfin.date.holiday.HolidayCalendarException;
import org.jfin.date.holiday.HolidayCalendarFactory;
import org.jfin.date.Period;

public class HolidayCalendarFactoryTestImpl extends HolidayCalendarFactory
{

	public HolidayCalendar getHolidayCalendar(String cityIdentifier)
			throws HolidayCalendarException
	{

		return getHolidayCalendar(cityIdentifier,Period.class);
	}

	public <T extends Period> HolidayCalendar<T> getHolidayCalendar(String cityIdentifier, Class<T> c) {
		if (cityIdentifier.equals("LnB"))
		{
			return new LondonHolidayCalendar<T>();
		} else if (cityIdentifier.equals("NyB"))
		{
			return new NewYorkHolidayCalendar<T>();
		} else
		{
			throw new HolidayCalendarException("Unknown holiday city "
					+ cityIdentifier);
		}
	}

	@Override
	public String[] getAvailableLocales()
	{
		return new String[]{"LnB","NyB"};
	}

}
