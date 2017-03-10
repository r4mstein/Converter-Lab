package ua.r4mstein.converterlab.presentation.adapters.detail.view_holders;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.presentation.adapters.detail.base.ViewHolderBase;
import ua.r4mstein.converterlab.presentation.adapters.detail.data_holders.CurrencyDataHolder;
import ua.r4mstein.converterlab.presentation.ui_models.CurrenciesModel;

import static ua.r4mstein.converterlab.util.Constants.DETAIL_FRAGMENT_COLOR_GREEN;

public final class CurrencyViewHolder extends ViewHolderBase<CurrencyDataHolder> {

    private TextView nameTextView;
    private TextView askTextView;
    private TextView bidTextView;
    private ImageView arrowAskImageView;
    private ImageView arrowBidImageView;

    private Context mContext;

    public CurrencyViewHolder(View itemView, Context context) {
        super(itemView);
        mContext = context;

        nameTextView = (TextView) itemView.findViewById(R.id.detail_currency_name);
        askTextView = (TextView) itemView.findViewById(R.id.detail_currency_ask);
        bidTextView = (TextView) itemView.findViewById(R.id.detail_currency_bid);
        arrowAskImageView = (ImageView) itemView.findViewById(R.id.detail_currency_ask_arrow);
        arrowBidImageView = (ImageView) itemView.findViewById(R.id.detail_currency_bid_arrow);
    }

    @Override
    public void setData(CurrencyDataHolder data) {
        super.setData(data);
        configureCurrencyViewHolder(data.currenciesModel);
    }

    private void configureCurrencyViewHolder(CurrenciesModel model) {

        if (model != null) {
            nameTextView.setText(model.getName());

            askTextView.setText(model.getAsk());
            if (model.getAsk_color().equals(DETAIL_FRAGMENT_COLOR_GREEN)) {
                askTextView.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                arrowAskImageView.setImageResource(R.drawable.ic_arrow_up_green);
            } else {
                askTextView.setTextColor(mContext.getResources().getColor(R.color.colorRed));
                arrowAskImageView.setImageResource(R.drawable.ic_arrow_down_red);
            }

            bidTextView.setText(model.getBid());
            if (model.getBid_color().equals(DETAIL_FRAGMENT_COLOR_GREEN)) {
                bidTextView.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                arrowBidImageView.setImageResource(R.drawable.ic_arrow_up_green);
            } else {
                bidTextView.setTextColor(mContext.getResources().getColor(R.color.colorRed));
                arrowBidImageView.setImageResource(R.drawable.ic_arrow_down_red);
            }
        }
    }
}
