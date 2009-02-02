/*******************************************************************************
 * Copyright (c) 1998, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation from Oracle TopLink
 ******************************************************************************/  
package org.eclipse.persistence.testing.tests.types;

import java.util.*;

import java.sql.*;

import oracle.jdbc.*;

import org.eclipse.persistence.sessions.*;
import org.eclipse.persistence.testing.framework.*;
import org.eclipse.persistence.internal.helper.Helper;
import org.eclipse.persistence.internal.platform.database.oracle.TIMESTAMPHelper;
import org.eclipse.persistence.internal.sessions.AbstractSession;

/**
 * This class is the super class of TIMESTAMP, TIMESTAMPTZ and TIMESTAMPLTZ specific
 * test cases.  The current subclasses are TIMESTAMPDirectToFieldTester and TIMESTAMPTypeConversionTester
 * that test Direct-to-Field or TypeConversion mapping.
 */
public abstract class TIMESTAMPTester extends TypeTester {

    public Timestamp tsToTS;
    public Timestamp tsToTSTZ;
    public Timestamp tsToTSLTZ;
    public Calendar calToTSTZ;
    public Calendar calToTSLTZ;
    public java.util.Date utilDateToTS;
    public java.util.Date utilDateToTSTZ;
    public java.util.Date utilDateToTSLTZ;
    public java.sql.Date dateToTS;
    public java.sql.Date dateToTSTZ;
    public java.sql.Date dateToTSLTZ;
    public Time timeToTS;
    public Time timeToTSTZ;
    public Time timeToTSLTZ;

    // This tsToTS will be stored in DATE field to make sure Timestamp -> DATE is backward compatible.
    public Timestamp tsToDate;
    // This Calendar will be stored in DATE field to make sure Calendar -> DATE is backward compatible.
    // ** So... why was this commmented out?  Is it backward compatible??
    //	public Calendar calToDate;
    public String sessionTimeZone;
    
    // isTimestampInGmt==true if driverVersion is 11.1.0.7 or later and
    // oracleConnection's property "oracle.jdbc.timestampTzInGmt" is set to "true".
    // The flag indicates whether TIMESTAMPTZ keeps its timestamp in GMT.
    public static boolean isTimestampInGmt;

    public TIMESTAMPTester() {
        super("NEW");
    }

    public TIMESTAMPTester(String nameOfTest, int year, int month, int date, int hrs, int min, int sec, int nano, 
                           int zoneMillis) {
        super(nameOfTest);

        calToTSTZ = Calendar.getInstance();
        calToTSTZ.getTimeZone().setRawOffset(zoneMillis);
        calToTSTZ.set(year, month, date, hrs, min, sec);
        calToTSTZ.set(Calendar.MILLISECOND, nano / 1000000);
        calToTSLTZ = calToTSTZ;
        buildUtilDates(calToTSTZ.getTime());
        buildTimestamps(utilDateToTS.getTime());
        buildDates(Helper.dateFromTimestamp(tsToTS));
        buildTimes(Helper.timeFromTimestamp(tsToTS));
        //		calToDate = c;
    }

    public TIMESTAMPTester(String nameOfTest, int year, int month, int date, int hrs, int min, int sec, int nano, 
                           String zoneId) {
        super(nameOfTest);

        /*		TimeZone tz = TimeZone.getDefault();
		tz.setID(zoneId);
		calToTSTZ = Calendar.getInstance(tz);*/
        calToTSTZ = Calendar.getInstance(TimeZone.getTimeZone(zoneId));
        calToTSTZ.set(year, month, date, hrs, min, sec);
        calToTSTZ.set(Calendar.MILLISECOND, nano / 1000000);
        calToTSLTZ = calToTSTZ;
        buildUtilDates(calToTSTZ.getTime());
        buildTimestamps(utilDateToTS.getTime());
        buildDates(Helper.dateFromTimestamp(tsToTS));
        buildTimes(Helper.timeFromTimestamp(tsToTS));
        //		calToDate = c;
    }

    public TIMESTAMPTester(String nameOfTest, Calendar c) {
        super(nameOfTest);
        calToTSTZ = c;
        calToTSLTZ = calToTSTZ;
        buildUtilDates(calToTSTZ.getTime());
        buildTimestamps(utilDateToTS.getTime());
        buildDates(Helper.dateFromTimestamp(tsToTS));
        buildTimes(Helper.timeFromTimestamp(tsToTS));
        //		calToDate = c;
    }

    public TIMESTAMPTester(String nameOfTest, long time) {
        super(nameOfTest);
        buildTimestamps(time);
        buildDates(Helper.dateFromTimestamp(tsToTS));
        buildTimes(Helper.timeFromTimestamp(tsToTS));
        buildUtilDates(new java.util.Date(time));
        calToTSTZ = Calendar.getInstance();
        calToTSTZ.setTime(utilDateToTS);
        calToTSLTZ = calToTSTZ;
        //		calToDate = c;
    }

    public void setSessionTimezone(String timeZone) {
        this.sessionTimeZone = timeZone;
    }
    
    protected void setup(Session session) {
        super.setup(session);
    }
    
    public void buildTimestamps(long time) {
        tsToTS = new Timestamp(time);
        tsToTSTZ = tsToTS;
        tsToTSLTZ = tsToTS;
        tsToDate = tsToTS;
    }

    public void buildUtilDates(java.util.Date date) {
        utilDateToTS = date;
        utilDateToTSTZ = date;
        utilDateToTSLTZ = date;
    }

    public void buildDates(java.sql.Date date) {
        dateToTS = date;
        dateToTSTZ = date;
        dateToTSLTZ = date;
    }

    public void buildTimes(Time time) {
        timeToTS = time;
        timeToTSTZ = time;
        timeToTSLTZ = time;
    }

    public Timestamp getTsToTS() {
        return tsToTS;
    }

    public Timestamp getTsToTSTZ() {
        return tsToTSTZ;
    }

    public Timestamp getTsToTSLTZ() {
        return tsToTSLTZ;
    }

    public Calendar getCalToTSTZ() {
        return calToTSTZ;
    }

    public Calendar getCalToTSLTZ() {
        return calToTSLTZ;
    }

    public Timestamp getTsToDate() {
        return tsToDate;
    }

    public java.util.Date getUtilDateToTS() {
        return utilDateToTS;
    }

    public java.util.Date getUtilDateToTSTZ() {
        return utilDateToTSTZ;
    }

    public java.util.Date getUtilDateToTSLTZ() {
        return utilDateToTSLTZ;
    }

    public java.sql.Date getDateToTS() {
        return dateToTS;
    }

    public java.sql.Date getDateToTSTZ() {
        return dateToTSTZ;
    }

    public java.sql.Date getDateToTSLTZ() {
        return dateToTSLTZ;
    }

    public Time getTimeToTS() {
        return timeToTS;
    }

    public Time getTimeToTSTZ() {
        return timeToTSTZ;
    }

    public Time getTimeToTSLTZ() {
        return timeToTSLTZ;
    }

    public void setTsToTS(java.sql.Timestamp aTimestamp) {
        tsToTS = aTimestamp;
    }

    public void setTsToTSTZ(java.sql.Timestamp aTimestamp) {
        tsToTSTZ = aTimestamp;
    }

    public void setTsToTSLTZ(java.sql.Timestamp aTimestamp) {
        tsToTSLTZ = aTimestamp;
    }

    public void setCalToTSTZ(Calendar calToTSTZ) {
        this.calToTSTZ = calToTSTZ;
    }

    public void setCalToTSLTZ(Calendar calToTSLTZ) {
        this.calToTSLTZ = calToTSLTZ;
    }

    public void setTsToDate(java.sql.Timestamp aTimestamp) {
        tsToDate = aTimestamp;
    }

    public void setUtilDateToTS(java.util.Date aDate) {
        utilDateToTS = aDate;
    }

    public void setUtilDateToTSTZ(java.util.Date aDate) {
        utilDateToTSTZ = aDate;
    }

    public void setUtilDateToTSLTZ(java.util.Date aDate) {
        utilDateToTSLTZ = aDate;
    }

    public void setDateToTS(java.sql.Date aDate) {
        dateToTS = aDate;
    }

    public void setDateToTSTZ(java.sql.Date aDate) {
        dateToTSTZ = aDate;
    }

    public void setDateToTSLTZ(java.sql.Date aDate) {
        dateToTSLTZ = aDate;
    }

    public void setTimeToTS(Time aDate) {
        timeToTS = aDate;
    }

    public void setTimeToTSTZ(Time aDate) {
        timeToTSTZ = aDate;
    }

    public void setTimeToTSLTZ(Time aDate) {
        timeToTSLTZ = aDate;
    }

    /*	public Calendar getCalToDate() {
		return calToDate;
	}

	public void setCalToDate(Calendar calToTSTZ) {
		this.calToDate = calToTSTZ;
	}*/

    public String toString() {
        return getTestName() + " -> " + TIMESTAMPHelper.printCalendar(calToTSTZ);
    }

    // Calendar's time zone is inserted into the db if the test runs either with binding or with native sql -
    // otherwise time zone is simply not present in the inserting sql and therefore
    // Calendar-to-TIMESTAMPTZ and Calendar-to-TIMESTAMPLTZ comparisons fail because
    // the objects are written into the db with connection's sessionTimeZone instead of the calendar's time zone. 
    boolean doesTestInsertCalendarTimeZone(WriteTypeObjectTest test) {
        return test.shouldBindAllParameters()==null || test.shouldBindAllParameters() || test.shouldUseNativeSQL();
    }

    // In case connection's session is not default and isTimestampInGmt==true
    // tsToTSTZ, utilDateToTSTZ and timeToTSTZ don't work.
    // Note that dateToTSTZ and calToTSTZ work.
    // The reason is Oracle jdbc bug 8206596:
    // Timestamp for 5pm in default zone (say NewYork) written into TIMESTAMPTZ using setObject
    // is inserted into the db as 5pm LosAngeles (in case it's set as a session time zone).
    // That happens with both 11.1.0.6 and 11.1.0.7 ojdbc versions.
    // When the result is read back 11.1.0.7 driver (in case timestampTzInGmt prop. is set to true - which is default setting)
    // correctly reads 5pm LA - and fails comparison with 5pm NY,
    // however version 11.0.0.6 (and 11.1.0.7 with timestampTzInGmt prop. set to false)
    // incorrectly reads back 5pm in default zone - so the inserted and the read back objects are equal.
    // Note that this incorrect reading happens only in case the target Java type is Timestamp (util.Date, Time),
    // Eclipselink corrects the result in case the target type is Calendar.
    boolean doesTimestampTZWork() {
        return TimeZone.getDefault().getID().equals(this.sessionTimeZone) || !isTimestampInGmt; 
    }
    
    protected void test(WriteTypeObjectTest testCase) {
        try {
            if(this.sessionTimeZone != null) {
                getConnection((DatabaseSession)testCase.getSession()).setSessionTimeZone(this.sessionTimeZone);
            }
        } catch (Exception exception) {
            throw new TestProblemException("Error setting timezone on connection.", exception);
        }
        super.test(testCase);
    }

    protected void verify(WriteTypeObjectTest testCase) throws TestException {
        try {
            super.verify(testCase);
        } catch (TestException e) {
            TIMESTAMPTester fromDatabase = (TIMESTAMPTester)testCase.getObjectFromDatabase();
            if (fromDatabase == null) {
                throw new TestErrorException("Value from database is null: " + e);
            }

            String errorMsg = "";
            if (!tsToTS.equals(fromDatabase.getTsToTS())) {
                errorMsg += "The tsToTS should be: " + tsToTS + ", but was read as: " + fromDatabase.getTsToTS() + "\n";
            }
            if(doesTimestampTZWork()) {
                if (!tsToTSTZ.equals(fromDatabase.getTsToTSTZ())) {
                    errorMsg += "The tsToTSTZ should be: " + tsToTSTZ + ", but was read as: " + fromDatabase.getTsToTSTZ() + "\n";
                }
            }
            if (!tsToTSLTZ.equals(fromDatabase.getTsToTSLTZ())) {
                errorMsg += "The tsToTSLTZ should be: " + tsToTSLTZ + ", but was read as: " + fromDatabase.getTsToTSLTZ() + "\n";
            }
            if (!utilDateToTS.equals(fromDatabase.getUtilDateToTS())) {
                errorMsg += "The utilDateToTS should be: " + utilDateToTS + ", but was read as: " + fromDatabase.getUtilDateToTS() + "\n";
            }
            if(doesTimestampTZWork()) {
                if (!utilDateToTSTZ.equals(fromDatabase.getUtilDateToTSTZ())) {
                    errorMsg += "The utilDateToTSTZ should be: " + utilDateToTSTZ + ", but was read as: " + fromDatabase.getUtilDateToTSTZ() + "\n";
                }
            }
            if (!utilDateToTSLTZ.equals(fromDatabase.getUtilDateToTSLTZ())) {
                errorMsg += "The utilDateToTSLTZ should be: " + utilDateToTSLTZ + ", but was read as: " + fromDatabase.getUtilDateToTSLTZ() + "\n";
            }
            if (!dateToTS.equals(fromDatabase.getDateToTS())) {
                errorMsg += "The dateToTS should be: " + dateToTS + ", but was read as: " + fromDatabase.getDateToTS() + "\n";
            }
            if (!dateToTSTZ.equals(fromDatabase.getDateToTSTZ())) {
                errorMsg += "The dateToTSTZ should be: " + dateToTSTZ + ", but was read as: " + fromDatabase.getDateToTSTZ() + "\n";
            }
            if (!dateToTSLTZ.equals(fromDatabase.getDateToTSLTZ())) {
                errorMsg += "The dateToTSLTZ should be: " + dateToTSLTZ + ", but was read as: " + fromDatabase.getDateToTSLTZ() + "\n";
            }
            if (!timeToTS.equals(fromDatabase.getTimeToTS())) {
                errorMsg += "The timeToTS should be: " + timeToTS + ", but was read as: " + fromDatabase.getTimeToTS() + "\n";
            }
            if(doesTimestampTZWork()) {
                if (!timeToTSTZ.equals(fromDatabase.getTimeToTSTZ())) {
                    errorMsg += "The timeToTSTZ should be: " + timeToTSTZ + ", but was read as: " + fromDatabase.getTimeToTSTZ() + "\n";
                }
            }
            if (!timeToTSLTZ.equals(fromDatabase.getTimeToTSLTZ())) {
                errorMsg += "The timeToTSLTZ should be: " + timeToTSLTZ + ", but was read as: " + fromDatabase.getTimeToTSLTZ() + "\n";
            }

            String originalCal = TIMESTAMPHelper.printCalendar(calToTSLTZ);
            String dbCal = TIMESTAMPHelper.printCalendar(fromDatabase.getCalToTSLTZ());
            //calToTSLTZ from database should have the sessionTimeZone
            if (sessionTimeZone!=null && !fromDatabase.getCalToTSLTZ().getTimeZone().getID().trim().equals(sessionTimeZone.trim())) {
                errorMsg += "The sessionTimeZone should be: " + sessionTimeZone + ", but was read as: " + 
                                             fromDatabase.getCalToTSLTZ().getTimeZone().getID();
            }

            //The original calToTSLTZ is converted to a Calendar based on the session time zone on read, 
            //which may not be the same as the original calToTSLTZ (although they represent 
            //the same GMT time).  Need to assign the original time zone to the calToTSLTZ to make them the same.
            //Calendar-to-TIMESTAMPLTZ does not work if calendar's time stamp is not inserted into the db.
            if (doesTestInsertCalendarTimeZone(testCase)) {
                Calendar tempCal = fromDatabase.getCalToTSLTZ();
                tempCal.getTimeZone().setID(calToTSLTZ.getTimeZone().getID());
                tempCal.getTimeZone().setRawOffset(calToTSLTZ.getTimeZone().getRawOffset());
                tempCal.setTime(tempCal.getTime());
                dbCal = TIMESTAMPHelper.printCalendar(tempCal);
                if (!originalCal.equals(dbCal)) {
                    errorMsg += "The calToTSLTZ should be: " + originalCal + ", but was read as: " + 
                                                 dbCal + " after being converted to the original timezone";
                }
            }

            //Calendar-to-TIMESTAMPTZ does not work if calendar's time stamp is not inserted into the db.
            if (doesTestInsertCalendarTimeZone(testCase)) {
                originalCal = TIMESTAMPHelper.printCalendar(calToTSTZ);
                dbCal = TIMESTAMPHelper.printCalendar(fromDatabase.getCalToTSTZ());
                if (!originalCal.equals(dbCal)) {
                    errorMsg += "The calToTSTZ should be: " + originalCal + ", but was read as: " + dbCal; 
                }
            }

            //DATE field does not store milliseconds.  Need to compare the original tsToTS without milliseconds with
            //the one from the database.
            Timestamp objectTimeInCache = getTsToDate();
            int originalTS = objectTimeInCache.getNanos();
            //Some original tsToDate without milliseconds fell through the Calendar check and need to excluded from
            //the following check.
            //			if (originalTS != 0) {

            // TsToDate always looses nanos, so do not throw an error if the ts match without the nanos.
            objectTimeInCache.setNanos(0);
            Timestamp objectTimeInDB = fromDatabase.getTsToDate();
            if (objectTimeInCache.equals(objectTimeInDB)) {
                if (originalTS != objectTimeInDB.getNanos()) {
                    // Nanos don't match, ok, as everything else has been checked, assume this was the reason for the error.
                    if(errorMsg.length() == 0) {
                        return;
                    }
                } else if (!calToTSLTZ.equals(fromDatabase.getCalToTSLTZ())) {
                    // This seems to be ok, as the timezones are not suppose to come back the same??
                    // So ok, as everything else has been checked, assume this was the reason for the error.
                    if(errorMsg.length() == 0) {
                        return;
                    }
                }
            } else {
                objectTimeInCache.setNanos(originalTS);
                errorMsg += "The tsToDate should be: " + objectTimeInCache + " but were read back as: " + 
                                             objectTimeInDB;
            }
            if(errorMsg.length() > 0) {
                throw new TestErrorException("\n"+errorMsg, e);
            } else {
                throw e;
            }
        }
    }

    private OracleConnection getConnection(DatabaseSession session) {
        Connection connection = ((AbstractSession)session).getAccessor().getConnection();
        return (OracleConnection)session.getServerPlatform().unwrapConnection(connection);
    }
}
