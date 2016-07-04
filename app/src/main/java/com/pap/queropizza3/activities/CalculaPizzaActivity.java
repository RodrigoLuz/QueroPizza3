package com.pap.queropizza3.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.pap.queropizza3.R;

public class CalculaPizzaActivity extends AppCompatActivity {

    SeekBar seekBarIndice, seekBarHomens, seekBarMulheres;
    TextView txtvPedacos, txtvHomens, txtvMulheres;
    double fome = 100;
    int homens = 0, mulheres = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcula_pizza);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtvPedacos = (TextView)findViewById(R.id.txtvPedacos);
        txtvHomens = (TextView)findViewById(R.id.txtvHomens);
        txtvMulheres =(TextView)findViewById(R.id.txtvMulheres);

        seekBarIndice = (SeekBar)findViewById(R.id.seekBarIndice);
        seekBarHomens = (SeekBar)findViewById(R.id.seekBarHomens);
        seekBarMulheres = (SeekBar)findViewById(R.id.seekBarMulheres);

        seekBarIndice.setMax(100);
        seekBarHomens.setMax(10);
        seekBarMulheres.setMax(10);

        seekBarIndice.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                fome = progress + 100;
                calculaPizza();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarHomens.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                homens = progress;
                txtvHomens.setText("Homens " + progress);
                calculaPizza();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarMulheres.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mulheres = progress;
                txtvMulheres.setText("Mulheres e crianças " + progress);
                calculaPizza();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void calculaPizza() {
        int pedacos;
        double calculo;
        calculo = ((homens * 3) + (mulheres * 2)) * (fome / 100);

        pedacos = (int) Math.round(calculo);
        txtvPedacos.setText("Pedaços: " + pedacos);
    }
}
