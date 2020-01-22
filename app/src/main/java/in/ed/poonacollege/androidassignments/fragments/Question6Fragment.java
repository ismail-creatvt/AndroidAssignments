package in.ed.poonacollege.androidassignments.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.ed.poonacollege.androidassignments.NonDraggableViewPager;
import in.ed.poonacollege.androidassignments.R;
import in.ed.poonacollege.androidassignments.Utility;
import in.ed.poonacollege.androidassignments.model.Question;

public class Question6Fragment extends Fragment implements ViewPager.OnPageChangeListener, MCQFragment.OnOptionItemSelectedListener {

    public static final String QUESTION = "QUESTION";
    private NonDraggableViewPager questionViewPager;
    private Button nextButton;
    private TextView questionCounter;
    private List<Question> questions;
    private HashMap<Question, Integer> answers=new HashMap<>();

    public Question6Fragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_question6, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        questionViewPager = view.findViewById(R.id.question_viewpager);
        nextButton = view.findViewById(R.id.next_button);
        questionCounter = view.findViewById(R.id.question_counter);

        String questionJson = Utility.getJsonStringFromRawFile(getContext(), R.raw.questions);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Question>>(){}.getType();
        questions = gson.fromJson(questionJson, type);

        questionCounter.setText(1+"/"+questions.size());

        questionViewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager(),FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT ) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                MCQFragment mcqFragment = new MCQFragment();
                Bundle data = new Bundle();
                data.putParcelable(QUESTION, questions.get(position));
                mcqFragment.setArguments(data);
                mcqFragment.setOnOptionsItemSelectedListener(Question6Fragment.this);
                return mcqFragment;
            }

            @Override
            public int getCount() {
                return questions.size();
            }
        });

        questionViewPager.addOnPageChangeListener(this);
        nextButton.setOnClickListener(v -> {
            int currentPosition = questionViewPager.getCurrentItem();
            if(currentPosition < questions.size()-1){
                currentPosition++;
                if(currentPosition == questions.size()-1){
                    nextButton.setText(R.string.finish);
                }
                questionViewPager.setCurrentItem(currentPosition);
            } else if(currentPosition == questions.size()-1){
                int correctAnswers = 0;
                for(Map.Entry<Question,Integer> q:answers.entrySet()){
                    if(q.getKey().getCorrectOptionIndex() == q.getValue()){
                        correctAnswers++;
                    }
                }
                if(getActivity() != null){
                    NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    Bundle data = Question6FragmentDirections.actionQuestion6FragmentToFinalResultFragment()
                            .setCorrectAnswers(correctAnswers)
                            .setTotalQuestions(questions.size())
                            .getArguments();
                    navController.navigate(R.id.action_question6Fragment_to_finalResultFragment, data);
                }
            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        questionCounter.setText((position+1)+"/"+questions.size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onOptionSelected(Question question, int index) {
        answers.put(question, index);
    }
}
