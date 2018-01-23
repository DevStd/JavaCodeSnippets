package exercise.string;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 날짜 처리 예제
 * */
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
	
	/**
	 * 오늘 날짜 구하기
	 * @return 현재 날짜/시각이 들어있는 Date객체
	 */
	public Date today() {
		Calendar c = Calendar.getInstance();
		return c.getTime();
	}
	
	/**
	 * 24시간 표시형식의 문자열로 변환 
	 * @param date 문자열로 변환할 Date 객체
	 * @return yyyy.MM.dd HH:mm:ss 형식의 문자열
	 */
	public String toDateString24(Date date) {
		String dateFormat = "yyyy.MM.dd HH:mm:ss";
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat);
		return sf.format(date);
	}
	
	/**
	 * 12시간 표시형식의 문자열로 변환 <br/>
	 * 언어와 무관하게 오전 오후는 AM/PM으로 표시함
	 * @param date 문자열로 변환할 Date 객체
	 * @return yyyy.MM.dd hh:mm:ss a 형식의 문자열
	 */
	public String toDateString12(Date date) {
		String dateFormat = "yyyy.MM.dd hh:mm:ss a";
		SimpleDateFormat sf = new SimpleDateFormat(dateFormat,Locale.US);
		return sf.format(date);
	}
	
	/**
	 * 년월일 값을 Date객체로 변환
	 * @param year 년
	 * @param month 월 (1월:1 ~ 12월:12)
	 * @param date 일
	 * @return
	 */
	public Date getDate(int year, int month, int date) {
		Calendar c = Calendar.getInstance();
		c.set(year, month-1, date, 0, 0, 0);
		return c.getTime();
	}
	
	/**
	 * 입력받은 날로부터 과거/미래의 시간 구함
	 * @param date 기준날짜
	 * @param interval 시간단위 ({@link Calendar#MONTH}, {@link Calendar#DAY}, {@link Calendar#HOUR}, ...)
	 * @param amount 시간간격
	 * @return 기준시간으로부터 시간간격만큼 떨어진 날짜/시간을 구함
	 */
	public Date interval(Date date, int interval, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(interval, amount);
		return c.getTime();
	}
	
	/**
	 * 해당날짜가 기준날짜로부터 몇 일째인지 구함 (시간단위는 무시함)
	 * @param from 기준시각
	 * @param to 구하고자하는 날짜
	 * @return 기준날짜로부터 몇 일째 인지 구한 값
	 */
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
