package a15110314_15110349.vn.edu.hcmute.lapitchat;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.FragmentActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Context;
import android.graphics.Color;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class RequestsFragment extends Fragment {
    private RecyclerView mRequestList;
    private View mMainView;
    private DatabaseReference FriendsRequestsReference;
    private FirebaseAuth mAuth;
    private DatabaseReference mUsersReference;



    String online_user_id;

    public RequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mMainView=inflater.inflate(R.layout.fragment_requests,container,false);
        mRequestList=(RecyclerView) mMainView.findViewById(R.id.requests_list);
        mAuth=FirebaseAuth.getInstance();
        online_user_id=mAuth.getCurrentUser().getUid();

        FriendsRequestsReference=FirebaseDatabase.getInstance().getReference().child("Friend_req").child(online_user_id);

        mUsersReference=FirebaseDatabase.getInstance().getReference().child("Users");



        mRequestList.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mRequestList.setLayoutManager(linearLayoutManager);


        return mMainView;

    }


    @Override
    public void onStart() {

        super.onStart();
        FirebaseRecyclerAdapter<Requests,RequestViewHolder> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<Requests, RequestViewHolder>(
                        Requests.class,
                        R.layout.friend_request_users_layout,
                        RequestsFragment.RequestViewHolder.class,
                        FriendsRequestsReference
                ) {
                    @Override
                    protected void populateViewHolder(final RequestViewHolder viewHolder, Requests model, int position) {
                        final String list_users_id=getRef(position).getKey();
                        mUsersReference.child(list_users_id).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final String userName = dataSnapshot.child("name").getValue().toString();
                                final String userThumb = dataSnapshot.child("image").getValue().toString();
                                final String userStatus=dataSnapshot.child("status").getValue().toString();
                                viewHolder.setUserName(userName);
                                viewHolder.setUserImage(userThumb,getContext());
                                viewHolder.setUserStatus(userStatus);
                                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        CharSequence options[] = new CharSequence[]{"Open Profile"};

                                        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                                        builder.setTitle("Select Options");
                                        builder.setItems(options, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                                //Click Event for each item.
                                                if(i == 0){

                                                    Intent profileIntent = new Intent(getContext(), ProfileActivity.class);
                                                    profileIntent.putExtra("user_id", list_users_id);
                                                    startActivity(profileIntent);

                                                }



                                            }
                                        });

                                        builder.show();

                                    }
                                });


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });


                    }
                };
        mRequestList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public RequestViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }

        public void setUserName(String userName) {
            TextView userNameView = (TextView) mView.findViewById(R.id.request_profile_name);
            userNameView.setText(userName);
        }
        public void setUserImage(String image, Context ctx){

            CircleImageView userImageView = (CircleImageView) mView.findViewById(R.id.request_profile_image);
            Picasso.with(ctx).load(image).placeholder(R.drawable.default_avatar).into(userImageView);

        }

        public void setUserStatus(String userStatus) {
            TextView userStatusView=(TextView) mView.findViewById(R.id.request_profile_status);
            userStatusView.setText(userStatus);
        }
    }

}

