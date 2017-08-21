package com.oceanmanaus.lab.oceancoffee;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final int PRECO_BASE = 5; // Pŕeco base do copo de cafe
    /**
     * Todas os itens do nosso pedido
     */
    int quantidade = 1; // Quantidade de copos
    private String nome; // Nome do cliente
    private boolean temCreme; // Sabe se o cliente deseja com creme
    private boolean temChocolate; // Sabe se o cliente deseja com chocolate
    private int preco; // Preco pedido
    private String pedido; // Sumario do pedido

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Cria o layout do nosso app
        setContentView(R.layout.activity_main);
    }

    /**
     * Metodo para SOLICITAR o copo de cafe
     * @param view - botão 'enviar' de @{@link layout/activity_main}
     */
    public void submitOrder(View view){

        // Seleciona campo 'nome' no layout
        EditText nameView = (EditText) findViewById(R.id.name);
        nome = nameView.getText().toString();

        // Seleciona o campo 'creme' e 'chocolate' no layout
        CheckBox whippedCreamView = (CheckBox) findViewById(R.id.whippedCream);
        CheckBox chocolateView = (CheckBox) findViewById(R.id.chocolate);
        // Verifica se o cliente marcou o campo 'creme' e 'chocolate' no layout
        temCreme = whippedCreamView.isChecked(); // True se estiver marcado, False se tiver desmarcado
        temChocolate = chocolateView.isChecked(); // True se estiver marcado, False se tiver desmarcado

        // Calcula o preco do cafe e retorna o 'total'
        preco = calculatePrice();
        // Cria o sumario do nosso 'pedido'
        pedido = createOrderSummary();

        // Seleciona o campo sumario, nosso TextView para mostrar o 'pedido'
        TextView summaryView = (TextView) findViewById(R.id.summaryOrder);
        // Seta para o TextView o 'pedido' formatado
        summaryView.setText(pedido);

        // Mostra os log do nosso pedido
        displayPrice();


        // Carrega o nosso pedido e dispara para o Gmail ou outro app de email
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));// Only email - ex: mailto: thiago.marques@oceanbrasil.com
        intent.putExtra(Intent.EXTRA_SUBJECT, "Pedido feito por " + nome); // Assunto do email
        intent.putExtra(Intent.EXTRA_TEXT, pedido); // Mensagem do email

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }

    /**
     * Metodo para aumentar a quantidade de copos
     * @param view - botão '+' de @{@link layout/activity_main}
     */
    public void increment(View view){
        // Se quantidade for maior que 3 nao permite aumentar a quantidade
        if(quantidade >= 3){
            displayMessage("Só é permitido pedir até 3");
            return;
        }
        // Aumenta a quantidade em 1
        quantidade++;
        // Mostra a quantidade atual
        displayQuantity();
    }

    /**
     * Metodo para diminuir a quantidade de copos
     * @param view - botão '-' de @{@link layout/activity_main}
     */
    public void decrement(View view){
        // Se quantidade for menor que 1 nao permite diminuir a quantidade
        if(quantidade <= 1){
            displayMessage("É necessário pedir pelo menos 1 copo");
            return;
        }
        // Diminui a quantidade em 1
        quantidade--;
        // Mostra a quantidade atual
        displayQuantity();
    }

    /**
     * Metodo para mostrar mensagens na tela com um Toast
     * @param message - Texto da mensagem
     */
    private void displayMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Log do pedido para visualizar no Logcat
     */
    private void displayPrice(){
        Log.d("Debug", "Name : " + nome);
        Log.d("Debug", "Add temCreme? " + temCreme);
        Log.d("Debug", "Add temChocolate? " + temChocolate);
        Log.d("Debug", "Price : " + preco);
        Log.i("Debug", pedido);
    }

    /**
     * Metodo para mostrar a quantidade atual no layout @{@link layout/activity_main}
     */
    private void displayQuantity(){
        // Seleciona o TextView da quantidade no layout
        TextView quantityView = (TextView) findViewById(R.id.quantity_text_view);
        // Seta a quantidade atual
        quantityView.setText(quantidade + "");
    }

    /**
     * Cria o sumario do nosso 'pedido'
     * @return pedido formatado
     */
    private String createOrderSummary(){
        String message = getString(R.string.name) + nome;
        message += "\n" + getString(R.string.hasWhippedCream) + temCreme;
        message += "\n" + getString(R.string.hasChocolate) + temChocolate;
        message += "\n" + getString(R.string.quantity) + quantidade; // Message = pedido + ""
        message += "\n" + getString(R.string.price) + preco;
        message += "\n" + getString(R.string.thank_you);

        return message;
    }

    /**
     * Metodo para calcular o preco do pedido
     * @return
     */
    private int calculatePrice(){

        // Preco por copo
        int precoPorCopo = PRECO_BASE;

        // Verifica se o cliente deseja com creme
        if(temCreme){
            // Adiciona o preco do creme
            precoPorCopo += 1; // precoPorCopo = precoPorCopo + 1
        }

        // Verifica se o cliente deseja com chocolate
        if(temChocolate){
            // Adiciona o preco do chocolate
            precoPorCopo += 2;
        }

        // Retorna o preco final do pedido
        return quantidade * precoPorCopo;
    }
}
