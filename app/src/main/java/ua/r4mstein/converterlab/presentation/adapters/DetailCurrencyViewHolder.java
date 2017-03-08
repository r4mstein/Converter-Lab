package ua.r4mstein.converterlab.presentation.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ua.r4mstein.converterlab.R;

public final class DetailCurrencyViewHolder extends RecyclerView.ViewHolder {

    public TextView nameTextView;
    public TextView askTextView;
    public TextView bidTextView;
    public ImageView arrowAskImageView;
    public ImageView arrowBidImageView;

    public DetailCurrencyViewHolder(View itemView) {
        super(itemView);

        nameTextView = (TextView) itemView.findViewById(R.id.detail_currency_name);
        askTextView = (TextView) itemView.findViewById(R.id.detail_currency_ask);
        bidTextView = (TextView) itemView.findViewById(R.id.detail_currency_bid);
        arrowAskImageView = (ImageView) itemView.findViewById(R.id.detail_currency_ask_arrow);
        arrowBidImageView = (ImageView) itemView.findViewById(R.id.detail_currency_bid_arrow);
    }
}
