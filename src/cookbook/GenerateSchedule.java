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

import org.jfin.date.*;
import org.jfin.date.util.ISDADateFormat;

import java.util.Calendar;
import java.util.List;
import java.text.ParseException;

public class GenerateSchedule {
	public static void main(String[] args) throws ScheduleException, ParseException {

		// Create a calendar with today's date
		Calendar today = Calendar.getInstance();

		// Ask the schedule generator to create a list of periods
		// starting today, continuing for 5Y (five years)
		// where the frequency is quarterly with a StubType of none
		List<Period> schedule = ScheduleGenerator.generateSchedule(today,"5Y", Frequency.QUARTERLY, StubType.NONE);

		// Print out the periods in the schedule
		for(Period period: schedule) {
			// Use the ISDADateFormat class to help format the calendars properly
			System.out.println(ISDADateFormat.format(period.getStartCalendar())+" to "+ISDADateFormat.format(period.getEndCalendar()));
		}

//		Outputs:
//		2006/10/27 to 2007/1/27
//		2007/1/27 to 2007/4/27
//		2007/4/27 to 2007/7/27
//		2007/7/27 to 2007/10/27
//		2007/10/27 to 2008/1/27
//		2008/1/27 to 2008/4/27
//		2008/4/27 to 2008/7/27
//		2008/7/27 to 2008/10/27
//		2008/10/27 to 2009/1/27
//		2009/1/27 to 2009/4/27
//		2009/4/27 to 2009/7/27
//		2009/7/27 to 2009/10/27
//		2009/10/27 to 2010/1/27
//		2010/1/27 to 2010/4/27
//		2010/4/27 to 2010/7/27
//		2010/7/27 to 2010/10/27
//		2010/10/27 to 2011/1/27
//		2011/1/27 to 2011/4/27
//		2011/4/27 to 2011/7/27
//		2011/7/27 to 2011/10/27


	}
}
