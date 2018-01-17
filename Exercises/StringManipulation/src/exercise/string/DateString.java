package exercise.string;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateString {
	public static void main(String[] args) {
		DateString ds = new DateString();
		
		// 1. 오늘 날짜 출력 (24시간제 , 12시간AM/PM 구분)
		Date today = ds.today();
		System.out.println("[today] "+ds.toDateString24(today));
		System.out.println("[today] "+ds.toDateString12(today));
		
		System.out.println("-----------------------------------------");
		
		// 2. 임의의 날짜 생성 (2010년 7월 1일)
		Date date = ds.getDate(2010, 7, 1);
		System.out.println("[2010년7월1일] "+ds.toDateString24(date));
		
		System.out.println("-----------------------------------------");
		
		// 3.1 현재시간 기준 한시간 뒤, 12시간 전/후 날짜 시간 출력
		Date HourAfter1 = ds.interval(ds.today(), Calendar.HOUR, 1);
		System.out.println("[한시간 뒤] "+ds.toDateString24(HourAfter1));
		Date HourBefore12 = ds.interval(ds.today(), Calendar.HOUR, -12);
		System.out.println("[12시간 전] "+ds.toDateString24(HourBefore12));
		Date HourAfter12 = ds.interval(ds.today(), Calendar.HOUR, 12);
		System.out.println("[12시간 후] "+ds.toDateString24(HourAfter12));
		
		// 3.2 6개월뒤, 1년뒤 날짜 출력
		Date MonthLater = ds.interval(ds.today(), Calendar.MONTH, 6);
		System.out.println("[6개월뒤] "+ds.toDateString24(MonthLater));
		Date YearLater = ds.interval(ds.today(), Calendar.YEAR, 1);
		System.out.println("[1년뒤] "+ds.toDateString24(YearLater));
		
		System.out.println("-----------------------------------------");
		
		// 4. 2010년 7월 1일 부터 오늘이 몇일째 되는 날인지 구하기
		Date from = ds.getDate(2010, 7, 1);
		Date to = ds.today();
		System.out.println("[From20100701] "+ds.dayDifference(from, to));
	}
	
	public Date today() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}
	
	public String toDateString24(Date date) {
		String dateFormat = "yyyy.MM.dd HH:mm:ss";
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
		return sf.format(date);
	}
	
	public String toDateString12(Date date) {
		String dateFormat = "yyyy.MM.dd hh:mm:ss a";
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat,Locale.US);
		return sf.format(date);
	}
	
	public Date getDate(int year, int month, int date) {
		Calendar c = Calendar.getInstance();
		c.set(year, month-1, date, 0, 0, 0);
		return c.getTime();
	}
	
	public Date interval(Date date, int interval, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(interval, amount);
		return c.getTime();
	}
	
	public long dayDifference(Date from, Date to) {
		Calendar c = Calendar.getInstance();		
		c.setTime(from);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		long tf = c.getTimeInMillis();
				
		c.setTime(to);
		c.set(Calendar.HOUR, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		long tt = c.getTimeInMillis();
		
		return (tt-tf)/(1000*60*60*24) + 1;
	}
}
