package com.ruby.x.json2.Views;

/**
 * Created by x on 30/05/2017.
 */
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.ruby.x.json2.Controllers.DetailController;
import com.ruby.x.json2.Models.DataConstant;
import com.ruby.x.json2.Models.DataTask;
import com.ruby.x.json2.R;


public class ImageDialogFragment extends DialogFragment{
        public String lat;
    public String lng;
    private GoogleMap mMap;
    private DetailController controller;
    private TextView txtTitle,txtApellido,txtEstado,txtMunicipio, txtDescription, txtCreatedDate, txtLat, txtLng;
    private ImageView imgDocumentation, imgDocumentation1;
    private DataTask dataTask;
    private Bundle data;
    public String title, apellido,estado,municipio, description, created_date, task_id, file_documentation, file_documentation1;
    // ImageLoader imageLoader = new ImageLoader(getActivity());
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(ImageDialogFragment.STYLE_NO_TITLE, 0);
        controller = new DetailController((DetailActivity) getActivity());

        data = getActivity().getIntent().getExtras();
        title = data.getString("title");
        lng = data.getString("lng");
        apellido = data.getString("apellido");
        estado = data.getString("estado");
        municipio = data.getString("municipio");
        file_documentation = data.getString("file_documentation");
        file_documentation1 = data.getString("file_documentation1");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_dialog, container);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txttitle = (TextView) view.findViewById(R.id.txtTitle);
        TextView txtapellido = (TextView) view.findViewById(R.id.txtApellido);
        TextView txtestado = (TextView) view.findViewById(R.id.txtEstado);
        TextView txtmunicipio = (TextView) view.findViewById(R.id.txtMunicipio);
        imgDocumentation = (ImageView) view.findViewById(R.id.imgDocumentation);
        imgDocumentation1 = (ImageView) view.findViewById(R.id.imgDocumentation1);


        ImageView btnClose = (ImageView)view.findViewById(R.id.btn_close);

        txttitle.setText(title);
        txtapellido.setText(apellido);
        txtestado.setText(estado);
        txtmunicipio.setText(municipio);
        //new  ImageLoader(this).execute((DataConstant.ASSET_URL + file_documentation) + (DataConstant.ASSET_URL + file_documentation));
        //new ImageLoader(this).execute(DataConstant.ASSET_URL + file_documentation );
        //new ImageLoader(this).execute(DataConstant.ASSET_URL + file_documentation1);
        //Picasso.with(context).load("http://i.imgur.com/DvpvklR.png").into(imageView);


        Glide.with(this)
                .load(DataConstant.ASSET_URL + file_documentation)
                .into(imgDocumentation);
        Glide.with(this)
                .load(DataConstant.ASSET_URL + file_documentation1)
                .into(imgDocumentation1);

        //btnOK.setOnClickListener(onClickListener("Button OK clicked!"));
        //btnPlus.setOnClickListener(onClickListener("Button Plus Clicked!"));
       btnClose.setOnClickListener(onCloseClickListener());
    }

    private View.OnClickListener onCloseClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageDialogFragment.this.dismiss();
            }
        };
    }

    private View.OnClickListener onClickListener(final String msg) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        };
    }

}
