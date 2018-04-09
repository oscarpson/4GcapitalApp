package joslabs.a4gcapitalapp.reglogin;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import joslabs.a4gcapitalapp.R;

/**
 * Created by OSCAR on 4/9/2018.
 */

public class UsersViewHolder extends RecyclerView.ViewHolder {
    public TextView username,idno,locaton,station,dob;
    public ImageView imgprofile;;
    public UsersViewHolder(View itemView) {
        super(itemView);

        username=itemView.findViewById(R.id.txtusername);
        idno=itemView.findViewById(R.id.txtidno);
        station=itemView.findViewById(R.id.txtstation);
        locaton=itemView.findViewById(R.id.txtlocation);
        dob=itemView.findViewById(R.id.txtDob);
        imgprofile=itemView.findViewById(R.id.imgprofile);

    }
}
