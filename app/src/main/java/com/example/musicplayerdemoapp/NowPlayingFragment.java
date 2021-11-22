package com.example.musicplayerdemoapp;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link NowPlayingFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class NowPlayingFragment extends Fragment {


    //Initialize  variables

    TextView playerPosition,playerDuration;
    SeekBar seekBar;
    ImageButton previous_btn,play_btn,next_btn,pause_btn;
    MediaPlayer mediaPlayer;
    Handler handler=new Handler();
    Runnable runnable;




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NowPlayingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NowPlayingFragment newInstance(String param1, String param2) {
        NowPlayingFragment fragment = new NowPlayingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_now_playing, container, false);



        //Assign variables

        playerPosition= playerPosition.findViewById(R.id.playerPosition);
        playerDuration= playerDuration.findViewById(R.id.playerDuration);
        previous_btn= previous_btn.findViewById(R.id.previous_btn);
        play_btn= play_btn.findViewById(R.id.play_btn);
        pause_btn= pause_btn.findViewById(R.id.pause_btn);
        next_btn= next_btn.findViewById(R.id.next_btn);
        seekBar= seekBar.findViewById(R.id.seekbar);

        //Initialize media player
        mediaPlayer=MediaPlayer.create(this, R.raw.music);   //specify the folder location to fetch song list

        //fetching song list from the folder
        runnable=new Runnable() {
            @Override
            public void run() {
                //set progress on seek bar
                seekBar.setProgress(mediaPlayer.getCurrentPosition());
                //handler post delay for 0.5 seconds
                handler.postDelayed(this, 500);
            }
        };

        //Get duration of the media player
        int duration=mediaPlayer.getDuration();
        //Convert millisecond to minute and seconds
        String sDuration= convertFormat(duration);
        //set duration on the text view
        playerDuration.setText(sDuration);

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hide play button
                play_btn.setVisibility(View.GONE);
                //Show pause button
                pause_btn.setVisibility(View.VISIBLE);
                //start media player
                mediaPlayer.start();
                //set max on seekbar
                seekBar.setMax(mediaPlayer.getDuration());
                //start handler
                handler.postDelayed(runnable,0);
            }
        });
        pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Hide pause button
                pause_btn.setVisibility(View.GONE);
                //Show play button
                play_btn.setVisibility(View.VISIBLE);
                //Stop media player
                mediaPlayer.pause();
                //Stop handler
                handler.removeCallbacks(runnable);
            }
        });

    }

    @SuppressLint("DefaultLocale")
    private String convertFormat(int duration) {
        return String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(duration),TimeUnit.MILLISECONDS.toSeconds(duration),TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(duration)));

    }
}