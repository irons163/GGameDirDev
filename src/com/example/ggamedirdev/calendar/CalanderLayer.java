package com.example.ggamedirdev.calendar;

import java.util.Calendar;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.MonthDisplayHelper;
import android.view.MotionEvent;

import com.example.ggamedirdev.BitmapUtil;
import com.example.ggamedirdev.CommonUtil;
import com.example.ggamedirdev.R;
import com.example.try_gameengine.framework.Layer;
import com.example.try_gameengine.stage.StageManager;

public class CalanderLayer extends Layer{
	// private static int WEEK_TOP_MARGIN = 74;
	// private static int WEEK_LEFT_MARGIN = 40;
	// private static int CELL_WIDTH = 58;
	// private static int CELL_HEIGH = 53;
	// private static int CELL_MARGIN_TOP = 92;
	// private static int CELL_MARGIN_LEFT = 39;
	private static int WEEK_TOP_MARGIN = 74 / CommonUtil.screenHeight;
	private static int WEEK_LEFT_MARGIN = 40 / CommonUtil.screenWidth;
	private static int CELL_WIDTH = 58 / CommonUtil.screenWidth;
	private static int CELL_HEIGH = 53 / CommonUtil.screenHeight;
	private static int CELL_MARGIN_TOP = 92 / CommonUtil.screenHeight;
	private static int CELL_MARGIN_LEFT = 39 / CommonUtil.screenWidth;
	private static float CELL_TEXT_SIZE;

	private static final String TAG = "CalendarView";
	private Calendar mRightNow = null;
	private Drawable mWeekTitle = null;
	private Cell mToday = null;
	private Cell[][] mCells = new Cell[6][7];
	private OnCellTouchListener mOnCellTouchListener = null;
	MonthDisplayHelper mHelper, mHelper1, mHelper2, mHelper3;
	Drawable mDecoration1, mDecoration12, mDecoration13, mDecoration14,
			mDecoration15, mDecoration16, mDecoration17;
	Drawable mDecoration2 = null;
	Drawable mDecoration3 = null;
	private Cell mToday2, mToday3, mToday4, mToday5, mToday6, mToday7;
	
	public static int mcDay,mcYear,mcMonth;
	
	Resources res = StageManager.getCurrentStage().getResources();

	public interface OnCellTouchListener {
		public void onTouch(Cell cell);
	}

	public CalanderLayer() {
		// TODO Auto-generated constructor stub
		mDecoration1 = res.getDrawable(
				R.drawable.typeb_calendar_today);
		mDecoration12 = res.getDrawable(
				R.drawable.typeb_calendar_today);
		mDecoration13 = res.getDrawable(
				R.drawable.typeb_calendar_today);
		mDecoration14 = res.getDrawable(
				R.drawable.typeb_calendar_today);
		mDecoration15 = res.getDrawable(
				R.drawable.typeb_calendar_today);
		mDecoration16 = res.getDrawable(
				R.drawable.typeb_calendar_today);
		mDecoration17 = res.getDrawable(
				R.drawable.typeb_calendar_today);
		mDecoration2 = res.getDrawable(
				R.drawable.typeb_calendar_today);
		mDecoration3 = res.getDrawable(
				R.drawable.typeb_calendar_today);
		initCalendarView();
	}

	private void initCalendarView() {
		mRightNow = Calendar.getInstance();
//		// prepare static vars
		
//		// set background
//		setImageResource(R.drawable.background);
		
		Bitmap bg = BitmapFactory.decodeResource(res,
				R.drawable.ic_launcher);
		
		setBitmap(bg);
		
		mWeekTitle = res.getDrawable(R.drawable.calendar_week);

		mHelper = new MonthDisplayHelper(mRightNow.get(Calendar.YEAR),
				mRightNow.get(Calendar.MONTH));
		// mHelper1 = new
		// MonthDisplayHelper(EX10_03.calendar1.get(Calendar.YEAR),
		// EX10_03.calendar1.get(Calendar.MONTH));
		// mHelper2 = new
		// MonthDisplayHelper(EX10_03.calendar2.get(Calendar.YEAR),
		// EX10_03.calendar2.get(Calendar.MONTH));
		// mHelper3 = new
		// MonthDisplayHelper(EX10_03.calendar3.get(Calendar.YEAR),
		// EX10_03.calendar3.get(Calendar.MONTH));
	}

	private void initCells() {
		class _calendar {
			public int day;
			public boolean thisMonth;

			public _calendar(int d, boolean b) {
				day = d;
				thisMonth = b;
			}

			public _calendar(int d) {
				this(d, false);
			}
		}
		;
		_calendar tmp[][] = new _calendar[6][7];

		for (int i = 0; i < tmp.length; i++) {
			int n[] = mHelper.getDigitsForRow(i);
			for (int d = 0; d < n.length; d++) {
				if (mHelper.isWithinCurrentMonth(i, d))
					tmp[i][d] = new _calendar(n[d], true);
				else
					tmp[i][d] = new _calendar(n[d]);

			}
		}

		Calendar today = Calendar.getInstance();
		int thisDay1 = 0;
		int thisDay2 = 0;
		int thisDay3 = 0;
		int thisMonth1 = 0;
		int thisMonth2 = 0;
		int thisMonth3 = 0;
		int thisYear1 = 0;

		mToday = null;
		mToday2 = null;
		mToday3 = null;
		mToday4 = null;
		mToday5 = null;
		mToday6 = null;
		mToday7 = null;

//		if ((mHelper.getMonth() + 1) == CalendarActivity.mcMonth) {
//			// thisDay2 = EX10_03.calendar2.get(Calendar.DAY_OF_MONTH);
//			thisDay2 = CalendarActivity.mcDay;
//			thisMonth1 = CalendarActivity.mcMonth;
//			thisYear1 = CalendarActivity.mcYear;
//		}

		if ((mHelper.getMonth() + 1) == mcMonth) {
			// thisDay2 = EX10_03.calendar2.get(Calendar.DAY_OF_MONTH);
			thisDay2 = mcDay;
			thisMonth1 = mcMonth;
			thisYear1 = mcYear;
		}
		
		// build cells
		Rect Bound = new Rect(CELL_MARGIN_LEFT, CELL_MARGIN_TOP, CELL_WIDTH
				+ CELL_MARGIN_LEFT, CELL_HEIGH + CELL_MARGIN_TOP);
		for (int week = 0; week < mCells.length; week++) {
			for (int day = 0; day < mCells[week].length; day++) {
				if (tmp[week][day].thisMonth) {
					if (day == 0 || day == 6)
						mCells[week][day] = new RedCell(tmp[week][day].day,
								new Rect(Bound), CELL_TEXT_SIZE);
					else
						mCells[week][day] = new Cell(tmp[week][day].day,
								new Rect(Bound), CELL_TEXT_SIZE);
				} else {
					mCells[week][day] = new GrayCell(tmp[week][day].day,
							new Rect(Bound), CELL_TEXT_SIZE);
				}

				Bound.offset(CELL_WIDTH, 0); // move to next column
				Log.e("x", thisMonth1 + "");
				Log.e("xx", mHelper.getMonth() + "");
				// get today
				if (tmp[week][day].day == thisDay3 && tmp[week][day].thisMonth) {
					mToday = mCells[week][day];
					mDecoration3.setBounds(mToday.getBound());
				} else if (tmp[week][day].day == thisDay2
						&& thisMonth1 == (mHelper.getMonth() + 1)) {
					Log.e("xx", thisDay2 + "");

					int i = 6;
					i = i - day;
					thisDay2 = thisDay2 + i;

					if (i == 0) {
						mToday = mCells[week][0];
						mToday2 = mCells[week][1];
						mToday3 = mCells[week][2];
						mToday4 = mCells[week][3];
						mToday5 = mCells[week][4];
						mToday6 = mCells[week][5];
						mToday7 = mCells[week][6];
						mDecoration2.setBounds(mToday.getBound());

						mDecoration12.setBounds(mToday2.getBound());
						mDecoration13.setBounds(mToday3.getBound());

						mDecoration14.setBounds(mToday4.getBound());
						mDecoration15.setBounds(mToday5.getBound());
						mDecoration16.setBounds(mToday6.getBound());
						mDecoration17.setBounds(mToday7.getBound());
						thisMonth1 = 0;
					}

				} else if (tmp[week][day].day == thisDay1
						&& tmp[week][day].thisMonth) {
					mToday = mCells[week][day];

					mDecoration1.setBounds(mToday.getBound());

				}
			}
			Bound.offset(0, CELL_HEIGH); // move to next row and first column
			Bound.left = CELL_MARGIN_LEFT;
			Bound.right = CELL_MARGIN_LEFT + CELL_WIDTH;
		}
	}

//	@Override
//	public void onLayout(boolean changed, int left, int top, int right,
//			int bottom) {
//		Rect re = getDrawable().getBounds();
//		WEEK_LEFT_MARGIN = CELL_MARGIN_LEFT = (right - left - re.width()) / 2;
//		mWeekTitle.setBounds(WEEK_LEFT_MARGIN, WEEK_TOP_MARGIN,
//				WEEK_LEFT_MARGIN + mWeekTitle.getMinimumWidth(),
//				WEEK_TOP_MARGIN + mWeekTitle.getMinimumHeight());
//		initCells();
//		super.onLayout(changed, left, top, right, bottom);
//	}
	
	@Override
	public void setPosition(float x, float y) {
		// TODO Auto-generated method stub
		super.setPosition(x, y);
		
		RectF re = getFrame();
		WEEK_LEFT_MARGIN = CELL_MARGIN_LEFT = (int) ((getLeft()+getWidth() - getLeft() - re.width()) / 2);
		mWeekTitle.setBounds(WEEK_LEFT_MARGIN, WEEK_TOP_MARGIN,
				WEEK_LEFT_MARGIN + mWeekTitle.getMinimumWidth(),
				WEEK_TOP_MARGIN + mWeekTitle.getMinimumHeight());
		initCells();
	}
	
	@Override
	public void setSize(int w, int h) {
		// TODO Auto-generated method stub
		super.setSize(w, h);
		
		RectF re = getFrame();
		WEEK_LEFT_MARGIN = CELL_MARGIN_LEFT = (int) ((getLeft()+getWidth() - getLeft() - re.width()) / 2);
		mWeekTitle.setBounds(WEEK_LEFT_MARGIN, WEEK_TOP_MARGIN,
				WEEK_LEFT_MARGIN + mWeekTitle.getMinimumWidth(),
				WEEK_TOP_MARGIN + mWeekTitle.getMinimumHeight());
		initCells();
	}

	public void setTimeInMillis(long milliseconds) {
		mRightNow.setTimeInMillis(milliseconds);
		initCells();
//		this.invalidate();
	}

	public int getYear() {
		return mHelper.getYear();
	}

	public int getMonth() {
		return mHelper.getMonth();
	}

	public void nextMonth() {
		mHelper.nextMonth();
		initCells();
//		invalidate();
	}

	public void previousMonth() {
		mHelper.previousMonth();
		initCells();
//		invalidate();
	}

	public boolean firstDay(int day) {
		return day == 1;
	}

	public boolean lastDay(int day) {
		return mHelper.getNumberOfDaysInMonth() == day;
	}

	public void goToday() {
		Calendar cal = Calendar.getInstance();
		mHelper = new MonthDisplayHelper(cal.get(Calendar.YEAR),
				cal.get(Calendar.MONTH));
		initCells();
//		invalidate();
	}

	public Calendar getDate() {
		return mRightNow;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mOnCellTouchListener != null) {
			for (Cell[] week : mCells) {
				for (Cell day : week) {
					if (day.hitTest((int) event.getX(), (int) event.getY())) {
						mOnCellTouchListener.onTouch(day);
					}
				}
			}
		}
		return super.onTouchEvent(event);
	}

	public void setOnCellTouchListener(OnCellTouchListener p) {
		mOnCellTouchListener = p;
	}
	
	@Override
	public void drawSelf(Canvas canvas, Paint paint) {
		// draw background
		super.drawSelf(canvas, paint);
		mWeekTitle.draw(canvas);

		// draw cells
		for (Cell[] week : mCells) {
			for (Cell day : week) {
				day.draw(canvas);
			}
		}

		// draw today
		if (mDecoration1 != null && mToday != null) {
			mDecoration1.draw(canvas);
		}
		if (mDecoration12 != null && mToday2 != null) {
			mDecoration12.draw(canvas);
		}
		if (mDecoration13 != null && mToday3 != null) {
			mDecoration13.draw(canvas);
		}
		if (mDecoration14 != null && mToday4 != null) {
			mDecoration14.draw(canvas);
		}
		if (mDecoration15 != null && mToday5 != null) {
			mDecoration15.draw(canvas);
		}
		if (mDecoration16 != null && mToday6 != null) {
			mDecoration16.draw(canvas);
		}
		if (mDecoration17 != null && mToday7 != null) {
			mDecoration17.draw(canvas);
		}
		if (mDecoration2 != null && mToday != null) {
			mDecoration2.draw(canvas);
		}
		if (mDecoration3 != null && mToday != null) {
			mDecoration3.draw(canvas);
		}
	}

	public class GrayCell extends Cell {
		public GrayCell(int dayOfMon, Rect rect, float s) {
			super(dayOfMon, rect, s);
			mPaint.setColor(Color.LTGRAY);
		}
	}

	private class RedCell extends Cell {
		public RedCell(int dayOfMon, Rect rect, float s) {
			super(dayOfMon, rect, s);
			mPaint.setColor(0xdddd0000);
		}

	}
}
