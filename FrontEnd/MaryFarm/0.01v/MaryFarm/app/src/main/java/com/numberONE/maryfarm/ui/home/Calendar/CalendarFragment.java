package com.numberONE.maryfarm.ui.home.Calendar;

import static com.prolificinteractive.materialcalendarview.MaterialCalendarView.SELECTION_MODE_RANGE;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.numberONE.maryfarm.R;
import com.numberONE.maryfarm.Retrofit.Calendar.ItemModel;
import com.numberONE.maryfarm.Retrofit.Calendar.RetrofitFactory;
import com.numberONE.maryfarm.Retrofit.Calendar.RetrofitService;
import com.numberONE.maryfarm.databinding.FragmentCalendarBinding;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnRangeSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter;
import com.prolificinteractive.materialcalendarview.format.TitleFormatter;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendarFragment extends Fragment {
    private FragmentCalendarBinding binding;
// 변수 선언
    private final String TAG = this.getClass().getSimpleName();
    int checked_plant = -1;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("EEE, d MMM yyyy");
    @BindView(R.id.calendarView) MaterialCalendarView widget;
    // 리사이클러뷰
    RecyclerView recyclerView_plants, recyclerView_memo;
    RecyclerView.LayoutManager layoutManager_plants, layoutManager_memo;
    RecyclerView.Adapter adapter_plants, adapter_memo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        ViewGroup view = binding.getRoot();
        ButterKnife.bind(this, view);

// 서버에서 식물 받아와서 파라미터 전해주기
        // 식물 종류
        List<String> plantnameList = new ArrayList<>();
        final String[][] plantname = new String[1][1];
        // 디데이
        List<String> plantddayList = new ArrayList<>();
        final String[][] dday = new String[1][1];

//레트로핏으로 내 작물리스트 받아와서 위 리스트에 저장 (+ 달의 리스트 받아 와서 저장해야함 / 애초에 달로만 해도 될 거 같은데?)
        RetrofitService networkService = RetrofitFactory.create();
        networkService.getList()
            .enqueue(new Callback<List<ItemModel>>() {
            @Override
            public void onResponse(Call<List<ItemModel>> call, Response<List<ItemModel>> response){
                if(response.isSuccessful()){
                    Log.i(TAG, "onResponse: 서버와 연결");
                    Log.i(TAG, "onResponse:"+response.body());
                    // 리싸이클러뷰 만들어서 정보 주기 다른 파일에 안 쓰고 여기에 바로 쓸 때
                    // Myadapter adapter = new Myadapter(response.body());
                    // binding.calendarPlantsType.setAdapter(adapter);
                    List<ItemModel> body = response.body();
                    for(ItemModel m : body) {
                        plantnameList.add(m.getPlantid());
                        plantddayList.add(m.getPlanttitle());
                    }
                    plantname[0] = plantnameList.stream().toArray(String[]::new);
                    dday[0] = plantddayList.stream().toArray(String[]::new);

                    recyclerView_plants = binding.calendarPlantsType;
                    layoutManager_plants = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false);
                    recyclerView_plants.setLayoutManager(layoutManager_plants);

                    // 어댑터 연결
                    adapter_plants = new CalendarPlantsAdapter(plantname[0], dday[0]);
                    recyclerView_plants.setAdapter(adapter_plants);
                }
            }
                @Override
                public void onFailure(Call<List<ItemModel>> call, Throwable t){
                    Log.e(TAG, "onFailure: 서버 연결 실패");
                    Log.e(TAG, "onFailure:", t);
                }
            });
//레트로핏 끝
    // 작물 메모 클릭
        CalendarPickPlantAdapter.setOnItemClickListener(new CalendarPickPlantAdapter.OnItemClickListener() {
            //동작 구현
            @Override
            public void onItemClick(View v, int pos, int id) {
                ImageButton calendar_water = v.findViewById(R.id.calendar_water);
                ImageButton calendar_scissors = v.findViewById(R.id.calendar_scissors);
                ImageButton calendar_pill = v.findViewById(R.id.calendar_pill);
                ImageButton calendar_shovel = v.findViewById(R.id.calendar_shovel);
                ImageButton calendar_note = v.findViewById(R.id.calendar_note);
                switch (v.getId()) {
                    case R.id.calendar_water:
                        if ( id == 1 ) {
                            widget.addDecorator(new EventDecorator(Color.BLUE, Collections.singleton(widget.getSelectedDate()), getActivity()));
                        } else {
                            widget.addDecorator(new EventDecorator(Color.WHITE, Collections.singleton(widget.getSelectedDate()), getActivity()));
                        } break;
                    case R.id.calendar_scissors:
                        if ( id == 1 ) {
                            widget.addDecorator(new EventDecorator(Color.MAGENTA, Collections.singleton(widget.getSelectedDate()), getActivity()));
                        } else {
                            widget.addDecorator(new EventDecorator(Color.WHITE, Collections.singleton(widget.getSelectedDate()), getActivity()));
                        } break;
                    case R.id.calendar_pill:
                        if ( id == 1 ) {
                            widget.addDecorator(new EventDecorator(Color.CYAN, Collections.singleton(widget.getSelectedDate()), getActivity()));
                        } else {
                            widget.addDecorator(new EventDecorator(Color.WHITE, Collections.singleton(widget.getSelectedDate()), getActivity()));
                        } break;
                    case R.id.calendar_shovel:
                        if ( id == 1 ) {
                            widget.addDecorator(new EventDecorator(Color.RED, Collections.singleton(widget.getSelectedDate()), getActivity()));
                        } else {
                            widget.addDecorator(new EventDecorator(Color.WHITE, Collections.singleton(widget.getSelectedDate()), getActivity()));
                        } break;
                    case R.id.calendar_note:

                        if ( id == 1 ) {
                            widget.addDecorator(new EventDecorator(Color.GREEN, Collections.singleton(widget.getSelectedDate()), getActivity()));
                        } else {
                            widget.addDecorator(new EventDecorator(Color.WHITE, Collections.singleton(widget.getSelectedDate()), getActivity()));
                        } break;
                }
            }
        });
// 작물 체크박스 클릭 -> 재배 기간 보여주기
        CalendarPlantsAdapter.setOnPlantCheckListener(new CalendarPlantsAdapter.OnPlantCheckListener() {
            //동작 구현
            @Override
            public void onPlantCheck(View v, int pos, Boolean checked) {
//                ImageButton calendar_water = v.findViewById(R.id.calendar_water);
                if(checked) {
                    binding.calendarView.addDecorator(new RangeDecorator(getActivity()));
                    widget.setSelectionMode(SELECTION_MODE_RANGE);
                    for(int i=1;i<=10;i++){
                        widget.setDateSelected(CalendarDay.from(2023,02,i), true);
                    }
                    widget.setClickable(false);
                } else {
                    widget.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
                    binding.calendarView.addDecorator(new DayDecorator(getActivity()));
                }
            }
        });
// 일자 클릭 시
        final CalendarDay[] selecteddate = {null};
        binding.calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(
                    @NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Log.i(TAG, "onDateSelected: "+date);
                if(selecteddate[0] == date){
                    widget.setSelected(false);
                    binding.calendarPickDate.setVisibility(View.GONE);
                    selecteddate[0] = null;
                } else {
                    selecteddate[0] = date;
                    binding.calendarView.addDecorator(new DayDecorator(getActivity()));
                    widget.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
                    recyclerView_memo = binding.calendarPickDate;
                    layoutManager_memo = new LinearLayoutManager(getActivity());
                    recyclerView_memo.setLayoutManager(layoutManager_memo);
                    adapter_memo = new CalendarPickPlantAdapter(plantname[0], dday[0]);
                    recyclerView_memo.setAdapter(adapter_memo);
                    binding.calendarPickDate.setVisibility(View.VISIBLE);
                }
            }
        });
// 달력 커스텀
        // 첫 시작 요일이 일요일이 되도록 설정
        binding.calendarView.state()
                .edit()
                .setFirstDayOfWeek(DayOfWeek.of(Calendar.SATURDAY)) // 왜 토요일로 썻는데 일요일부터 시작할까
                .commit();
        // 월, 요일을 한글로 보이게 설정 (MonthArrayTitleFormatter의 작동을 확인하려면 밑의 setTitleFormatter()를 지운다)
        binding.calendarView.setTitleFormatter(new MonthArrayTitleFormatter(getResources().getTextArray(R.array.custom_months)));
        binding.calendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(getResources().getTextArray(R.array.custom_weekdays)));
        // 좌우 화살표 사이 연, 월의 폰트 스타일 설정
        binding.calendarView.setHeaderTextAppearance(R.style.CalendarWidgetHeader);
        // 요일 선택 시 내가 정의한 드로어블이 적용되도록 함
        binding.calendarView.setOnRangeSelectedListener(new OnRangeSelectedListener() {
            @Override
            public void onRangeSelected(@NonNull MaterialCalendarView widget, @NonNull List<CalendarDay> dates) {
                // 아래 로그를 통해 시작일, 종료일이 어떻게 찍히는지 확인하고 본인이 필요한 방식에 따라 바꿔 사용한다
                // UTC 시간을 구하려는 경우 이 라이브러리에서 제공하지 않으니 별도의 로직을 짜서 만들어내 써야 한다
                String startDay = dates.get(0).getDate().toString();
                String endDay = dates.get(dates.size() - 1).getDate().toString();
                Log.i(TAG, "시작일 : " + startDay + ", 종료일 : " + endDay);
                Log.i(TAG, "선택된날짜 : " + dates);
            }
        });
        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        binding.calendarView.addDecorators(new DayDecorator(getActivity()),
                new SundayDecorator(),
                new SaturdayDecorator());

        // 좌우 화살표 가운데의 연/월이 보이는 방식 커스텀
        binding.calendarView.setTitleFormatter(new TitleFormatter() {
            @Override
            public CharSequence format(CalendarDay day) {
                // CalendarDay라는 클래스는 LocalDate 클래스를 기반으로 만들어진 클래스다
                // 때문에 MaterialCalendarView에서 연/월 보여주기를 커스텀하려면 CalendarDay 객체의 getDate()로 연/월을 구한 다음 LocalDate 객체에 넣어서
                // LocalDate로 변환하는 처리가 필요하다
                LocalDate inputText = day.getDate();
                String[] calendarHeaderElements = inputText.toString().split("-");
                StringBuilder calendarHeaderBuilder = new StringBuilder();
                calendarHeaderBuilder.append(calendarHeaderElements[0])
                        .append(" ")
                        .append(calendarHeaderElements[1]);
                return calendarHeaderBuilder.toString();
            }
        });
// 달력 커스텀 끝
     return view;
    }

// 날짜 찾기 버튼 클릭 시
    @OnClick(R.id.pick_date)
    void onSelectedClicked() {
        showDatePickerDialog(getActivity(), widget.getSelectedDate(),
                (view, year, monthOfYear, dayOfMonth) ->
                        widget.setSelectedDate(CalendarDay.from(year, monthOfYear + 1, dayOfMonth))
        );
    }
    // 날짜 설정 모달 및 선택 이동
    public static void showDatePickerDialog(
            Context context, CalendarDay day,
            DatePickerDialog.OnDateSetListener callback) {
        if (day == null) {
            day = CalendarDay.today();
        }
        DatePickerDialog dialog = new DatePickerDialog(
                context, 0, callback, day.getYear(), day.getMonth() - 1, day.getDay()
        );
        dialog.show();
    }

// 아래로 데코레이터
    // 하루 표시
    private static class DayDecorator implements DayViewDecorator {
        private final Drawable drawable;
        public DayDecorator(Context context) {
            drawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector);
        }
        // true를 리턴 시 모든 요일에 내가 설정한 드로어블이 적용된다
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return true;
        }

        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        @Override
        public void decorate(DayViewFacade view) {
            view.setSelectionDrawable(drawable);
            //view.addSpan(new StyleSpan(Typeface.BOLD));   // 달력 안의 모든 숫자들이 볼드 처리됨
        }
    }
    // 여러날 표시
    private static class RangeDecorator implements DayViewDecorator {
        private final Drawable drawable;
        public RangeDecorator(Context context) {
            drawable = ContextCompat.getDrawable(context, R.drawable.calendar_range_selector);
        }
        // true를 리턴 시 모든 요일에 내가 설정한 드로어블이 적용된다
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return true;
        }

        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        @Override
        public void decorate(DayViewFacade view) {
            view.setSelectionDrawable(drawable);
            //view.addSpan(new StyleSpan(Typeface.BOLD));   // 달력 안의 모든 숫자들이 볼드 처리됨
        }
    }
    // 일요일 빨간색
    public class SundayDecorator implements DayViewDecorator {
        private final Calendar calendar = Calendar.getInstance();
        public SundayDecorator() {
        }
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            int sunday = day.getDate().with(DayOfWeek.SUNDAY).getDayOfMonth();
            return sunday == day.getDay();
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.RED));
        }
    }
    // 토요일 파란색
    public class SaturdayDecorator implements DayViewDecorator {
        private final Calendar calendar = Calendar.getInstance();
        public SaturdayDecorator() {
        }
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            int sunday = day.getDate().with(DayOfWeek.SATURDAY).getDayOfMonth();
            return sunday == day.getDay();
        }

        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new ForegroundColorSpan(Color.BLUE));
        }
    }
    // 이벤트에 점 표시
    public class EventDecorator implements DayViewDecorator {

        //private final Drawable drawable;
        private int color;
        private HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates, Activity context) {
            //drawable = context.getResources().getDrawable(R.drawable.more);
            this.color = color;
            this.dates = new HashSet<>(dates);
        }
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }
        @Override
        public void decorate(DayViewFacade view) {
            //view.setSelectionDrawable(drawable);
            view.addSpan(new DotSpan(5, color)); // 날자밑에 점
        }
    }
}

