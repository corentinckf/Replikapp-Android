package com.ckfcsteam.replikapp.fragments;

//Importation des bibliothèques
import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.ckfcsteam.replikapp.MainActivity;
import com.ckfcsteam.replikapp.R;
import com.ckfcsteam.replikapp.activities.LoginActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.security.Key;
import java.util.HashMap;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class ProfilFragment extends Fragment {

    /* DEBUT : Déclaration de variables */
    // firebase
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    String storagePath = "User_Profile_Cover/";

    //Dialogue pour l'edition des informations
    private ProgressDialog pd;

    private ImageView avatarProfil, coverProfil;
    public TextView nameProfil, mailProfil, phoneProfil;
    private Button logoutBtn, editProfilBtn;


    //Constantes permissions camera et le stockage
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int STORAGE_REQUEST_CODE = 200;
    private static final int IMAGE_PICK_GALLERY_REQUEST_CODE = 300;
    private static final int IMAGE_PICK_CAMERA_REQUEST_CODE = 400;

    //Listes des requêtes des permissions
    String cameraPermissions[];
    String storagePermissions[];

    //Uri de l'image choisie
    Uri image_uri;

    //Vérification du choix entre photo de profil ou photo de couverture
    private String profileOrCoverImg;


    /* FIN : Déclaration de variables */


    public ProfilFragment() { }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);



        // Initialisation de Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");
        storageReference = getInstance().getReference() ;

        //Initialisation des listes des permissions
        cameraPermissions = new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE};

        //récupération des ID XML
        avatarProfil = view.findViewById(R.id.avatarProfil);
        coverProfil =  view.findViewById(R.id.coverProfil);
        nameProfil = view.findViewById(R.id.nameProfil);
        mailProfil = view.findViewById(R.id.mailProfil);
        phoneProfil = view.findViewById(R.id.phoneProfil);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        editProfilBtn = view.findViewById(R.id.editProfilBtn);
        pd = new ProgressDialog(getActivity());




        /* Nous récupérons les informations de l'utilisateur connecté grâce à l'uid ou le mail (Ici ce sera l'uid).
        * Grâce à orderByChild de Query, nous regardons si la clé uid correspond à l'uid de l'utilisateur connecté
        * L'accès aux données sera donc possible et la récupération aussi grâce à getChildren */

        Query query = databaseReference.orderByChild("uid").equalTo(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //données obtenues
                for(DataSnapshot ds : dataSnapshot.getChildren()){

                    // On récupère les données
                    String name = ""+ds.child("name").getValue();
                    String email = ""+ds.child("email").getValue();
                    String phone = ""+ds.child("phone").getValue();
                    String image = ""+ds.child("image").getValue();
                    String cover = ""+ds.child("cover").getValue();


                    //On affiche dans le profil
                    String emailDisplay = getString(R.string.mailInfo) +"     "+ email;
                    String phoneDisplay = getString(R.string.phoneInfo) +"     "+ phone;

                    nameProfil.setText(name);
                    mailProfil.setText(emailDisplay);
                    phoneProfil.setText(phoneDisplay);

                    try {
                        //Si l'image est récupéré alors on la met en place sur l'imageview
                        Picasso.get().load(image).into(avatarProfil);
                    }catch (Exception e){
                        //Si il y a une exception quelconque lors de la récupération de l'image.

                    }

                    try {
                        //Si l'image est récupéré alors on la met en place sur l'imageview
                        Picasso.get().load(cover).into(coverProfil);
                    }catch (Exception e){
                        //Si il y a une exception quelconque lors de la récupération de l'image.
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Click du bouton d'édition de profil
        editProfilBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditProfileDialog();
            }
        });

        // Click du bouton de deconnexion
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle(R.string.logOutADTitle).setMessage(R.string.logOutADMessage).setPositiveButton(R.string.logOutADYes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        signOut();
                    }
                }).setNegativeButton(R.string.logOutADNo, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();

            }
        });



        return view;
    }


    /**
     * Vérifie si la permission d'accéder au "storage" est vraie ou non
     * @return true si le "storage" est choisi, sinon false
     */
    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(Objects.requireNonNull(getActivity()),Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }

    /**
     * requête d'accès au "storage"
     */
    private void requestStoragePermission(){
        requestPermissions(storagePermissions, STORAGE_REQUEST_CODE);
    }

    /**
     * Vérifie si la permission d'accéder à la camera est vraie ou non
     * @return true si la permission caméra et accès en écriture sont vérifiés.
     */
    private boolean checkCameraPermission(){
        boolean result = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    /**
     * requête d'accès à la camera
     */
    private void requestCameraPermission(){
        requestPermissions(cameraPermissions, CAMERA_REQUEST_CODE);
    }

    // Option de modification de profil
    /* Edition de l'image de profile
    *  Edition de l'image de couverture
    *  Edition du nom
    *  Edition du numéro de téléphone */

    /**
     * Méthode qui affiche un dialog android pour l'édition de profil
     */
    private void showEditProfileDialog() {

        String editProfilePic = getString(R.string.editProfilPic);
        String editCoverPic = getString(R.string.editCoverPic);
        String editName = getString(R.string.editProfileName);
        String editPhoneN = getString(R.string.editProfilePhone);
        String dialogTitle = getString(R.string.dialogTitle);

        final String updateProfilPic = getString(R.string.messUpdatePic);
        final String updateCover = getString(R.string.messUpdateCover);
        final String updateName = getString(R.string.messUpdateName);
        final String updatePhone = getString(R.string.messUpdatePhone);

        String options[] = {editProfilePic, editCoverPic, editName, editPhoneN};

        //Dialogue d'alerte
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //set title
        builder.setTitle(dialogTitle);
        //set items
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Si on clique sur le premier 'item' du dialog
                if(which == 0){
                    //Edit Profil picture
                    pd.setMessage(updateProfilPic);
                    profileOrCoverImg = "image";   // Choix de l'image de profil
                    showImagePicDialog();
                    // Si on clique sur le second 'item' du dialog
                }else if(which == 1){
                    //Edit Cover picture
                    pd.setMessage(updateCover);
                    profileOrCoverImg = "cover";     // Choix de l'image de couverture
                    showImagePicDialog();
                    // Si on clique sur le troisième 'item' du dialog
                }else if(which == 2){
                    //Edit Name
                    pd.setMessage(updateName);
                    showNamePhoneUpdateDialog("name");        // Méthode permettant la modification des données de la BD sur le nom (paramètre name)
                    // Si on clique sur le quatrième 'item' du dialog
                }else if(which == 3){
                    //Edit Phone number
                    pd.setMessage(updatePhone);
                    showNamePhoneUpdateDialog("phone");        // Méthode permettant la modification des données de la BD sur le numéro de tel (paramètre phone)
                }
            }
        });
        builder.create().show();
    }

    /**
     * Mathode permettant de mettre à jour son numéro de téléphone ou son nom/pseudo
     * @param key Prend 2 valeurs :
     *            - "name" : clé permettant de mettre à jour la base de donnée sur le nom/pseudo de l'utilisateur
     *            - "phone" : clé permettant de mettre à jour la base de donnée sur le num de tel de l'utilisateur
     */
    private void showNamePhoneUpdateDialog(final String key) {
        //dialog custom
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if(key == "name"){
            builder.setTitle(getString(R.string.updateName));
        }else{
            builder.setTitle(getString(R.string.updatePhone));
        }
        //Mise en place de la présentation du dialog
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(10,10,10,10);
        // Ajout d'un EditText
        final EditText editText = new EditText(getActivity());

        // ajout d'un texte selon le type de modification
        if(key == "name"){
            editText.setHint(getString(R.string.nameUpdateET));
        }else{
            editText.setHint(getString(R.string.phoneUpdateET));
        }
        linearLayout.addView(editText);

        builder.setView(linearLayout);

        //Ajout du bouton modifier dans le dialog
        builder.setPositiveButton(getString(R.string.updateText), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //récupération des données de l'editText
                String value = editText.getText().toString().trim();

                // Si champ vide
                if(!TextUtils.isEmpty(value)){
                    pd.show();
                    HashMap<String, Object> result = new HashMap<>();
                    result.put(key, value);

                    databaseReference.child(user.getUid()).updateChildren(result).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //update réussi
                            pd.dismiss();
                            Toast.makeText(getActivity(), getString(R.string.success), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pd.dismiss();
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    if(key == "name"){
                        Toast.makeText(getActivity(), getString(R.string.errorETName), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(), getString(R.string.errorETPhone), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Ajout du bouton modifier dans le dialog
        builder.setNegativeButton(getString(R.string.cancelUpdateBtn), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //Création et affichage du dialog
        builder.create().show();
    }

    /**
     * Methode permettant l'affichage d'un dialog pour le choix de l'image : caméra ou gestionnaire de fichiers. On vérifie l'état des permissions pour cet accès.
     */
    private void showImagePicDialog() {

        String camera = getString(R.string.camera);
        String gallery = getString(R.string.gallery);

        String pickImg = getString(R.string.pickImg);
        String options[] = {camera, gallery };

        //Dialogue d'alerte
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //set title
        builder.setTitle(pickImg);
        //set items
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == 0){
                    //Camera
                    if(!checkCameraPermission()){
                        requestCameraPermission();
                    }else{
                        pickFromCamera();
                    }
                }else if(which == 1){
                    //Galerie
                    if(!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        pickFromGallery();
                    }



                }
            }
        });
        builder.create().show();
    }

    /**
     * Cette méthode est appelé lorsque l'utilisateur appuie sur autoriser ou refuser la permission
     * @param requestCode Le code de la requête
     * @param permissions les permissions requises/demandées
     * @param grantResults le résultat obtenu
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        String toastRequestCDenied = getString(R.string.toastRequestCDenied);
        String toastRequestGDenied = getString(R.string.toastRequestGDenied);

        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                //cas de la caméra, vérifie les permissions
                if(grantResults.length > 0){
                    boolean camAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeStorAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(camAccepted && writeStorAccepted){
                        //Permission == true
                        pickFromCamera();
                    }else{
                        Toast.makeText(getActivity(), toastRequestCDenied, Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE:{
                if(grantResults.length > 0){
                    //cas de du gestionnaire d'images, vérifie les permissions
                    boolean writeStorAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if(writeStorAccepted){
                        //Permission == true
                        pickFromGallery();
                    }else{
                        Toast.makeText(getActivity(), toastRequestGDenied, Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
        }

    }

    /**
     * Méthode qui va appeler la méthode de versement de l'url de l'image dans la base de donnée ainsi que le storage, si les prérequis sont respectés.
     * @param requestCode Code de requête lorsque l'on effectue le choix des images
     * @param resultCode le code résultat
     * @param data la donnée récupérée.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // méthode appelée après avoir choisi une image depuis la camera ou la galerie.
        if(resultCode == RESULT_OK){
            if(requestCode == IMAGE_PICK_GALLERY_REQUEST_CODE){
                //Image prise depuis  la galerie, on récupère l'uri de l'image
                image_uri = data.getData();

                uploadProfileCoverPhoto(image_uri);

            }
        }
        if(requestCode == IMAGE_PICK_CAMERA_REQUEST_CODE){
            //Image prise depuis la camera, on récupère l'uri de l'image

            uploadProfileCoverPhoto(image_uri);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Méthode permettant "d'upload" l'image choisie préalablement par l'utilisateur.
     * @param uri lien de l'image
     */
    private void uploadProfileCoverPhoto(Uri uri) {

        final String sucMsgT = getString(R.string.success);
        //affichage de la progression
        pd.show();

        // String du chemin de d'accès à l'image, e.g. Users_Profile_Cover/profilePic_uid.extension

        String filePathAndName = storagePath+""+profileOrCoverImg+"_"+user.getUid();

        StorageReference storageReference2 = storageReference.child(filePathAndName);
        storageReference2.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // Si succès alors image ajouté au storage
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while(!uriTask.isSuccessful());
                Uri downloadUri = uriTask.getResult();

                //Vérification si l'image est upload ou pas et si l'url est reçu
                if(uriTask.isSuccessful()){
                    //Image uploadé
                    // On met à jour l'url dans la base de données.
                    HashMap<String, Object> results = new HashMap<>();
                    //Le premier paramètre correspond à la modification effectuée(cover ou profile picture)
                    //Le second à l'url de l'image
                    results.put(profileOrCoverImg, downloadUri.toString());

                    databaseReference.child(user.getUid()).updateChildren(results).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Url mise dans la base de données de l'utilisateur si succès, puis fermeture du dialog
                            pd.dismiss();
                            Toast.makeText(getActivity(), sucMsgT, Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Erreur lors de l'ajout de l'url dans la base de données si echec, fermeture du dialog
                            pd.dismiss();
                            Toast.makeText(getActivity(), R.string.error, Toast.LENGTH_SHORT).show();


                        }
                    });
                }else{
                    // erreur
                    pd.dismiss();
                    Toast.makeText(getActivity(), getString(R.string.error), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Si echec, affichage de l'erreur puis on ferme le dialogue
                pd.dismiss();
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Méthode permettant de choisir l'image venant de la caméra de l'appareil.
     */
    private void pickFromCamera() {
        // Instance de l'image choisie depuis la caméra
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp Pic");
        values.put(MediaStore.Images.Media.DESCRIPTION, "Temp Description");
        //Mise en place de l'image uri
        image_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Instance pour lancer la camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_REQUEST_CODE);


    }

    /**
     * Méthode permettant de choisir l'image venant de la galerie d'images.
     */
    private void pickFromGallery() {
        //Choix de l'image depuis le gestionnaire d'images
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, IMAGE_PICK_GALLERY_REQUEST_CODE);


    }

    /**
     * Méthode permettant la deconnexion de l'utilisateure et de la redirection vers l'activité de connexion
     */
    public void signOut(){
        firebaseAuth.signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }



}
