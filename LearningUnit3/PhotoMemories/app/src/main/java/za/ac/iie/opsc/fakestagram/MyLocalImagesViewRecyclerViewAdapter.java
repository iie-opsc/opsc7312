package za.ac.iie.opsc.fakestagram;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import za.ac.iie.opsc.fakestagram.dummy.DummyContent.DummyItem;
import za.ac.iie.opsc.fakestagram.model.ImageModel;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ImageModel}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyLocalImagesViewRecyclerViewAdapter extends
        RecyclerView.Adapter<MyLocalImagesViewRecyclerViewAdapter.ViewHolder> {

    private final List<ImageModel> mValues;

    public MyLocalImagesViewRecyclerViewAdapter(List<ImageModel> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mDescriptionView.setText(mValues.get(position).getImageName());
        holder.mImageView.setImageBitmap(mValues.get(position).getImageBitmap());
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mDescriptionView;
        public final ImageView mImageView;
        public ImageModel mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mDescriptionView = view.findViewById(R.id.txt_view_image_name);
            mImageView = view.findViewById(R.id.img_view_image_pane);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mDescriptionView.getText() + "'";
        }
    }
}