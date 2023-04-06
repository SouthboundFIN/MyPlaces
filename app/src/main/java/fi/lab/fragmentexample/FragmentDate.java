package fi.lab.fragmentexample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDate extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_DATE = "initdate";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LocalDate localDate;

    public FragmentDate() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentDate.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentDate newInstance(String param1, String param2, LocalDate localDate) {
        FragmentDate fragment = new FragmentDate();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        // LocalDate implements Serializable interface --> putSerializable() can be used
        args.putSerializable(ARG_DATE,localDate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            localDate = (LocalDate) getArguments().getSerializable(ARG_DATE);
        }
    }
    // the components are not yet available!
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date, container, false);
    }
    // the first event where the components are available is onViewCreated()!
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // fragment can not see the res id's.
        TextView textView = getActivity().findViewById(R.id.textView);
        textView.setText(mParam1 + " "+mParam2);
        CalendarView calendarView = getActivity().findViewById(R.id.calendarView);
        try {
            calendarView.setDate(new SimpleDateFormat("yyyy-MM-dd")
                    .parse(localDate.toString())
                    .getTime());
            calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        } catch (ParseException e) {
            //throw new RuntimeException(e);
        }
    }
}