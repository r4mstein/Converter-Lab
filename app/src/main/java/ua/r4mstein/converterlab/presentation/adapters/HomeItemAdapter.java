package ua.r4mstein.converterlab.presentation.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ua.r4mstein.converterlab.R;
import ua.r4mstein.converterlab.presentation.ui_models.OrganizationModel;

public final class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.HomeViewHolder> {

    private Context mContext;
    private List<OrganizationModel> mOrganizationList;

    public HomeItemAdapter(Context context, List<OrganizationModel> organizationList) {
        mContext = context;
        mOrganizationList = organizationList;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View itemView = inflater.inflate(R.layout.home_item, parent, false);
        return new HomeViewHolder(itemView, mContext);
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        OrganizationModel model = mOrganizationList.get(position);

        holder.titleTextView.setText(model.getTitle());
        holder.regionTextView.setText(model.getRegion());
        holder.cityTextView.setText(model.getCity());
        holder.phoneTextView.setText(model.getPhone());
        holder.addressTextView.setText(model.getAddress());

    }

    @Override
    public int getItemCount() {
        return mOrganizationList.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView titleTextView;
        public TextView regionTextView;
        public TextView cityTextView;
        public TextView phoneTextView;
        public TextView addressTextView;

        public ImageButton linkImageButton;
        public ImageButton locationImageButton;
        public ImageButton phoneImageButton;
        public ImageButton nextImageButton;

        public Context context;

        public HomeViewHolder(View itemView, Context context) {
            super(itemView);

            this.context = context;

            titleTextView = (TextView) itemView.findViewById(R.id.home_title);
            regionTextView = (TextView) itemView.findViewById(R.id.home_region);
            cityTextView = (TextView) itemView.findViewById(R.id.home_city);
            phoneTextView = (TextView) itemView.findViewById(R.id.home_phone);
            addressTextView = (TextView) itemView.findViewById(R.id.home_address);

            linkImageButton = (ImageButton) itemView.findViewById(R.id.home_link_ib);
            locationImageButton = (ImageButton) itemView.findViewById(R.id.home_location_ib);
            phoneImageButton = (ImageButton) itemView.findViewById(R.id.home_phone_ib);
            nextImageButton = (ImageButton) itemView.findViewById(R.id.home_next_ib);

            linkImageButton.setOnClickListener(this);
            locationImageButton.setOnClickListener(this);
            phoneImageButton.setOnClickListener(this);
            nextImageButton.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.home_link_ib:
                    Toast.makeText(context, "Link", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.home_location_ib:
                    Toast.makeText(context, "Location", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.home_phone_ib:
                    Toast.makeText(context, "Phone", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.home_next_ib:
                    Toast.makeText(context, "Next", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
