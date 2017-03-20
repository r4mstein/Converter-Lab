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

    public CurrencyViewHolder(final View _itemView, final Context _context) {
        super(_itemView);
        mContext = _context;

        nameTextView = (TextView) _itemView.findViewById(R.id.detail_currency_name);
        askTextView = (TextView) _itemView.findViewById(R.id.detail_currency_ask);
        bidTextView = (TextView) _itemView.findViewById(R.id.detail_currency_bid);
        arrowAskImageView = (ImageView) _itemView.findViewById(R.id.detail_currency_ask_arrow);
        arrowBidImageView = (ImageView) _itemView.findViewById(R.id.detail_currency_bid_arrow);
    }

    @Override
    public void setData(final CurrencyDataHolder _data) {
        super.setData(_data);
        configureCurrencyViewHolder(_data.currenciesModel);
    }

    private void configureCurrencyViewHolder(final CurrenciesModel _model) {

        if (_model != null) {
            nameTextView.setText(_model.getName());

            askTextView.setText(_model.getAsk());
            if (_model.getAskColor().equals(DETAIL_FRAGMENT_COLOR_GREEN)) {
                askTextView.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                arrowAskImageView.setImageResource(R.drawable.ic_arrow_up_green);
            } else {
                askTextView.setTextColor(mContext.getResources().getColor(R.color.colorRed));
                arrowAskImageView.setImageResource(R.drawable.ic_arrow_down_red);
            }

            bidTextView.setText(_model.getBid());
            if (_model.getBidColor().equals(DETAIL_FRAGMENT_COLOR_GREEN)) {
                bidTextView.setTextColor(mContext.getResources().getColor(R.color.colorGreen));
                arrowBidImageView.setImageResource(R.drawable.ic_arrow_up_green);
            } else {
                bidTextView.setTextColor(mContext.getResources().getColor(R.color.colorRed));
                arrowBidImageView.setImageResource(R.drawable.ic_arrow_down_red);
            }
        }
    }
}
