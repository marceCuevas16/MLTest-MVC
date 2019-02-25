package com.marcecuevas.mltestapp.view.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.marcecuevas.mltestapp.R;
import com.marcecuevas.mltestapp.controller.PaymentController;
import com.marcecuevas.mltestapp.model.MLError;
import com.marcecuevas.mltestapp.model.MLResultListener;
import com.marcecuevas.mltestapp.model.dto.PaymentMethodDTO;
import com.marcecuevas.mltestapp.utils.MLFont;
import com.marcecuevas.mltestapp.utils.MLFontVariable;
import com.marcecuevas.mltestapp.view.activity.BankActivity;
import com.marcecuevas.mltestapp.view.adapter.PaymentMethodAdapter;

import java.util.List;

import butterknife.BindView;

public class PaymentMethodFragment extends GenericFragment implements PaymentMethodAdapter.Listener {

    @BindView(R.id.methodsRV)
    protected RecyclerView methodsRV;

    @BindView(R.id.totalTV)
    protected TextView totalTV;

    @BindView(R.id.totalAmountTV)
    protected TextView totalAmountTV;

    private PaymentMethodAdapter adapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_payment_method;
    }

    @Override
    protected void init() {
        totalTV.setText(getString(R.string.total_label));
        totalTV.setTextSize(14);
        totalTV.setTextColor(getResources().getColor(R.color.colorDark));
        MLFont.applyFont(getContext(),totalTV, MLFontVariable.light);

        totalAmountTV.setText("$5000");
        totalAmountTV.setTextSize(16);
        totalAmountTV.setTextColor(getResources().getColor(R.color.colorDark));
        MLFont.applyFont(getContext(),totalAmountTV,MLFontVariable.bold);

        adapter = new PaymentMethodAdapter(getContext(),this);
        methodsRV.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        methodsRV.setAdapter(adapter);

        callService();
    }

    private void callService() {
        PaymentController controller = new PaymentController(getContext());
        controller.paymentMethods(new MLResultListener<List<PaymentMethodDTO>>() {
            @Override
            public void success(List<PaymentMethodDTO> result) {
                adapter.update(result);
            }

            @Override
            public void error(MLError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    @Override
    public void onPaymentMethodSelected(PaymentMethodDTO paymentMethod) {
        Intent intent = new Intent(getContext(), BankActivity.class);
        startActivity(intent);
    }
}
