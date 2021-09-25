package com.betharley.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView playerOneScore, playerTwoScore, playerStatus;
    private Button[] buttons = new Button[9];
    private Button resetGame;

    private int playerOneScoreCount, playerTwoScoreCount, rountCount;
    private boolean activePlayer;

    // p1 => 0
    // p2 => 1
    // empty => 2
    private int[] gameState = {2,2,2,2,2,2,2,2,2};
    private int[][] winningPosistions = {
            {0,1,2},{3,4,5},{6,7,8}, //rows
            {0,3,6},{1,4,7},{2,5,8}, //columns
            {0,4,8},{2,4,6}
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = findViewById(R.id.playerOneScore);
        playerTwoScore = findViewById(R.id.playerTwoScore);
        playerStatus = findViewById(R.id.playerStatus);

        resetGame = findViewById(R.id.resetGame);

        for( int i = 0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }

        rountCount = 0;
        playerOneScoreCount = 0;
        playerTwoScoreCount = 0;
        activePlayer = true;

        resetGame.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                playAgain();
                playerOneScoreCount = 0;
                playerTwoScoreCount = 0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });
    }

    @Override
    public void onClick(View v) {

        if(!((Button)v).getText().toString().equals("")){
            return;
        }

        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if(activePlayer){
            ((Button)v).setText("X");
            ((Button)v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 0;
        }else{
            ((Button)v).setText("O");
            ((Button)v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }

        rountCount++;
        if( chechWinner() ){
            if( activePlayer ){
                playerOneScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Jogador A Ganhou essa Rodada!!!", Toast.LENGTH_SHORT).show();
                playAgain();
            }else{
                playerTwoScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Jogador B Ganhou essa Rodada!!!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        }else if(rountCount == 9){
            playAgain();
            Toast.makeText(this, "Jogo Empatado!!!", Toast.LENGTH_SHORT).show();
        }else{
            activePlayer = !activePlayer;
        }

        if( playerOneScoreCount > playerTwoScoreCount){
            playerStatus.setText("Jogador A esta Ganhando!!");
        }else if( playerTwoScoreCount > playerOneScoreCount ){
            playerStatus.setText("Jogador B esta Ganhando!!!");
        }else{
            playerStatus.setText("Jogo esta Empatado!!!");
        }
    }

    public boolean chechWinner(){
        boolean winnerResult = false;

        for(int[] winningPosion: winningPosistions ){
            if( gameState[winningPosion[0]] == gameState[winningPosion[1]] &&
                gameState[winningPosion[1]] == gameState[winningPosion[2]] &&
                gameState[winningPosion[0]] != 2
            ){
                winnerResult = true;
            }

        }
        return winnerResult;
    }

    public void updatePlayerScore(){
        playerOneScore.setText(Integer.toString(playerOneScoreCount));
        playerTwoScore.setText(Integer.toString(playerTwoScoreCount));
    }

    public void playAgain(){
        rountCount = 0;
        activePlayer = true;
        for(int i = 0; i < buttons.length; i++){
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}