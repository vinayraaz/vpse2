package in.varadhismartek.patashalaerp.DashboardModule.Homework;

import android.app.DatePickerDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.ClassAndSection.ClassSectionModel;
import in.varadhismartek.patashalaerp.DashboardModule.Assessment.SectionSubjectModel;
import in.varadhismartek.patashalaerp.GalleryModule.GalleryAdapter;
import in.varadhismartek.patashalaerp.GeneralClass.CustomSpinnerAdapter;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

public class Fragment_HomeworkCreate extends Fragment implements View.OnClickListener {

    ImageView iv_backBtn;
    RecyclerView rv_URL, rv_attachment;
    TextView tv_book_minus, tv_book_count, tv_book_plus;
    TextView tv_url_minus, tv_url_count, tv_url_plus;
    Spinner spn_division, spn_class, spn_section, spn_Subject;
    EditText et_work_title, et_description;
    TextView tv_fromDate, tv_toDate;
    Button btn_submit;
    LinearLayout ll_book_row, ll_url_row;
    GalleryAdapter questionbankAdapter;
    int bookCount = 1, urlCount = 1;
    String currentDate = "", fromDate = "", toDate = "";
    int date, month, year;
    String str_class = "", strSection = "", str_subject = "";

    ArrayList<String> listBook;
    ArrayList<String> listUrl;
    ArrayList<ClassSectionModel> modelArrayList;
    ArrayList<EditText> etListBookName, etListPageNo, etListURL, etListUrlDesc;
    ArrayList<ImageView> ivBookImageList = new ArrayList<>();

    APIService mApiService;
    ArrayList<String> listDivision;
    ArrayList<String> listClass;
    ArrayList<String> listSection;
    ArrayList<String> listSectionNew;
    ArrayList<String> listSubject;
    ArrayList<String> referenceAttachImage;
    ArrayList<SectionSubjectModel> sectionSubjectModels;
    CustomSpinnerAdapter customSpinnerAdapter;
    private String strDiv = "";
    private static int FROM_GALLERY = 1;

    ImageView ivAttachImage;

    JSONObject obj = null, obj2 = null;

    String imageEncoded;
    List<String> imagesEncodedList = new ArrayList<>();


    List<String> imageFilePathList = new ArrayList<>();

    ArrayList<Uri> mArrayUri = new ArrayList<>();

    EditText etBookName, etPageNo, etUrl, ed_urlDesc;

    public Fragment_HomeworkCreate() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_homework_create, container, false);

        initViews(view);
        initListeners();

        getDivisionApi();


        View viewBook = inflater.inflate(R.layout.layout_book_row, null);
        ll_book_row.addView(viewBook);

        View viewURL = inflater.inflate(R.layout.layout_url_row, null);
        ll_url_row.addView(viewURL);

        etBookName = viewBook.findViewById(R.id.et_book_name);
        etPageNo = viewBook.findViewById(R.id.et_page_no);
        etUrl = viewURL.findViewById(R.id.et_url);
        ed_urlDesc = viewURL.findViewById(R.id.url_description);

        etListBookName.add(etBookName);
        etListPageNo.add(etPageNo);

        etListURL.add(etUrl);
        etListUrlDesc.add(ed_urlDesc);


        return view;
    }

    private void getCurrentDate() {

        Calendar cal = Calendar.getInstance();

        year = cal.get(java.util.Calendar.YEAR);
        month = cal.get(java.util.Calendar.MONTH);
        date = cal.get(Calendar.DAY_OF_MONTH);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        currentDate = dateFormat.format(cal.getTime());

        tv_fromDate.setText(currentDate);
        tv_toDate.setText(currentDate);

        Log.d("CURRENTDATE", currentDate);

        fromDate = currentDate;
        toDate = currentDate;

    }

    private void initListeners() {

        iv_backBtn.setOnClickListener(this);
        tv_book_minus.setOnClickListener(this);
        tv_book_plus.setOnClickListener(this);
        tv_url_minus.setOnClickListener(this);
        tv_url_plus.setOnClickListener(this);
        tv_fromDate.setOnClickListener(this);
        tv_toDate.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        ivAttachImage.setOnClickListener(this);
    }

    private void initViews(View view) {
        mApiService = ApiUtils.getAPIService();

        iv_backBtn = view.findViewById(R.id.iv_backBtn);

        rv_URL = view.findViewById(R.id.rv_URL);
        rv_attachment = view.findViewById(R.id.rv_attachment);

        tv_book_minus = view.findViewById(R.id.tv_book_minus);
        tv_book_count = view.findViewById(R.id.tv_book_count);
        tv_book_plus = view.findViewById(R.id.tv_book_plus);

        tv_url_minus = view.findViewById(R.id.tv_url_minus);
        tv_url_count = view.findViewById(R.id.tv_url_count);
        tv_url_plus = view.findViewById(R.id.tv_url_plus);

        spn_division = view.findViewById(R.id.spn_division);
        spn_class = view.findViewById(R.id.spn_class);
        spn_section = view.findViewById(R.id.spn_section);
        spn_Subject = view.findViewById(R.id.spn_Subject);

        et_work_title = view.findViewById(R.id.et_work_title);
        tv_fromDate = view.findViewById(R.id.tv_fromDate);
        tv_toDate = view.findViewById(R.id.tv_toDate);
        et_description = view.findViewById(R.id.et_description);

        btn_submit = view.findViewById(R.id.btn_submit);

        ll_book_row = view.findViewById(R.id.ll_book_row);
        ll_url_row = view.findViewById(R.id.ll_url_row);

        ivAttachImage = view.findViewById(R.id.iv_attachmentImage);

        listDivision = new ArrayList<>();
        listClass = new ArrayList<>();
        listSubject = new ArrayList<>();


        sectionSubjectModels = new ArrayList<>();


        listBook = new ArrayList<>();
        listUrl = new ArrayList<>();
        modelArrayList = new ArrayList<>();
        etListBookName = new ArrayList<>();
        etListPageNo = new ArrayList<>();
        // imageList = new ArrayList<>();
        referenceAttachImage = new ArrayList<>();


        etListURL = new ArrayList<>();
        etListUrlDesc = new ArrayList<>();

        listBook.add("1");
        listUrl.add("1");
        tv_book_count.setText(String.valueOf(bookCount));

    }


    private void getDivisionApi() {


        mApiService.getDivisions(Constant.SCHOOL_ID).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                listDivision.clear();
                listDivision.add(0, "Select Division");
                boolean statusDivision = false;
                if (response.isSuccessful()) {
                    try {
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = (String) object.get("status");

                        if (status.equalsIgnoreCase("success")) {
                            JSONArray jsonArray = object.getJSONArray("data");

                            Log.i("Dvision**!@**", "" + response.body());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object1 = jsonArray.getJSONObject(i);

                                String added_datetime = object1.getString("added_datetime");
                                String Id = object1.getString("id");
                                statusDivision = object1.getBoolean("status");
                                if (statusDivision) {
                                    String division = object1.getString("division");
                                    String school_id = object1.getString("school_id");
                                    String added_by_id = object1.getString("added_by_id");

                                    listDivision.add(division);
                                }


                            }
                            customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), listDivision);
                            spn_division.setAdapter(customSpinnerAdapter);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {

                   // Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {
                Log.d("GET_DIVISION_EXCEPTION", t.getMessage());

            }
        });

        spn_division.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                strDiv = parent.getSelectedItem().toString();
                //  setSpinnerForClass();

                JSONArray array = new JSONArray();
                JSONObject jsonObject = new JSONObject();

                try {
                    array.put(strDiv);
                    jsonObject.put("divisions", array);
                    Constant.DIVISION_NAME = strDiv;

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                getClassSectionList(jsonObject);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void getClassSectionList(JSONObject jsonObject) {

        listClass.clear();
        listClass.add(0, "Select Class");
        customSpinnerAdapter.notifyDataSetChanged();
        mApiService.getClassSections(Constant.SCHOOL_ID, jsonObject).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.i("CLASS_SECTIONDDD", "" + response.body());

                if (response.isSuccessful()) {
                    try {

                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = (String) object.get("status");

                        if (status.equalsIgnoreCase("success")) {
                            JSONObject jsonObject1 = object.getJSONObject("data");
                            if (object.getJSONObject("data").toString().equals("{}")) {
                                //modelArrayList.clear();
                                // customSpinnerAdapter.notifyDataSetChanged();
                                //   setSpinnerForClass();

                            } else {

                                JSONObject jsonObject2 = jsonObject1.getJSONObject(strDiv);
                                Iterator<String> keys = jsonObject2.keys();

                                while (keys.hasNext()) {
                                    String key = keys.next();
                                    JSONObject jsonObjectValue = jsonObject2.getJSONObject(key);
                                    String className = jsonObjectValue.getString("class_name");
                                    JSONArray jsonArray = jsonObjectValue.getJSONArray("sections");
                                    listSection = new ArrayList<>();

                                    String Section = "";

                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        Section = jsonArray.getString(i);
                                        listSection.add(Section);
                                    }

                                    listClass.add(className);

                                    modelArrayList.add(new ClassSectionModel(className, listSection));
                                    Gson gson = new Gson();

                                }
                            }


                        }

                    } catch (JSONException je) {

                    }

                } else {
                    try {
                        assert response.errorBody()!=null;
                        JSONObject object = new JSONObject(response.errorBody().string());
                        String message = object.getString("message");
                        Log.d("ADMIN_API", message);
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });

        customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), listClass);
        spn_class.setAdapter(customSpinnerAdapter);


        spn_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                str_class = spn_class.getSelectedItem().toString();
                listSectionNew = new ArrayList<>();
                boolean b = true;

                for (int j = 0; j < modelArrayList.size(); j++) {
                    if (modelArrayList.get(j).getClassName().contains(str_class)) {
                        listSectionNew.clear();

                        for (int k = 0; k < modelArrayList.get(j).getListSection().size(); k++) {

                            listSectionNew.add(modelArrayList.get(j).getListSection().get(k));

                        }
                        // getSubject(strDiv, str_class);
                    }
                }


                customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), listSectionNew);
                spn_section.setAdapter(customSpinnerAdapter);

                getSubject(strDiv, str_class);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spn_section.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                strSection = spn_section.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void getSubject(final String strDiv, final String str_class) {

        mApiService.getSubject(Constant.SCHOOL_ID, strDiv, str_class).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(Call<Object> call, Response<Object> response) {
                Log.i("SubjectList***", "" + response.body());

                if (response.isSuccessful()) {
                    try {
                        listSubject.clear();
                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = (String) object.get("status");

                        if (status.equalsIgnoreCase("success")) {
                            JSONObject jsonObject1 = object.getJSONObject("Section");

                            Iterator<String> keys = jsonObject1.keys();

                            while (keys.hasNext()) {
                                String sectionkey = keys.next();
                                JSONObject jsonSection = jsonObject1.getJSONObject(sectionkey);
                                Iterator<String> keys2 = jsonSection.keys();

                                while (keys2.hasNext()) {
                                    String subjectkey = keys2.next();
                                    JSONObject jsonSubject = jsonSection.getJSONObject(subjectkey);

                                    String strType = jsonSubject.getString("subject_type");
                                    String strCode = jsonSubject.getString("subject_code");
                                    boolean strStatus = jsonSubject.getBoolean("status");
                                    listSubject.add(subjectkey);


                                }
                                customSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), listSubject);
                                spn_Subject.setAdapter(customSpinnerAdapter);
                                // setRecyclerView();
                            }
                        }
                    } catch (JSONException je) {

                    }
                }
            }

            @Override
            public void onFailure(Call<Object> call, Throwable t) {

            }
        });
        spn_Subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_subject = spn_Subject.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void onClick(View view) {

        assert getActivity() != null;

        switch (view.getId()) {


            case R.id.iv_attachmentImage:
                Intent galleryIntent = new Intent();
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(galleryIntent, "Select Image"), FROM_GALLERY);


                break;
            case R.id.iv_backBtn:
                getActivity().onBackPressed();
                break;

            case R.id.tv_book_minus:

                if (bookCount > 1) {
                    bookCount = bookCount - 1;
                    listBook.remove(bookCount - 1);
                    tv_book_count.setText(String.valueOf(bookCount));
                    View viewBook = getLayoutInflater().inflate(R.layout.layout_book_row, null);
                    ll_book_row.removeViewAt(bookCount - 1);
                    etListBookName.remove(bookCount - 1);
                    etListPageNo.remove(bookCount - 1);
                    //  imageList.remove(bookCount - 1);

                }

                break;

            case R.id.tv_book_plus:

                if (bookCount < 5) {
                    bookCount = bookCount + 1;
                    listBook.add("bookCount-1");
                    tv_book_count.setText(String.valueOf(bookCount));
                    View viewBook = getLayoutInflater().inflate(R.layout.layout_book_row, null);
                    ll_book_row.addView(viewBook);

                    EditText etBookName = viewBook.findViewById(R.id.et_book_name);
                    EditText etPageNo = viewBook.findViewById(R.id.et_page_no);

                    etListBookName.add(etBookName);
                    etListPageNo.add(etPageNo);

                }

                break;

            case R.id.tv_url_minus:

                if (urlCount > 1) {
                    urlCount = urlCount - 1;
                    listUrl.remove(urlCount - 1);
                    tv_url_count.setText(String.valueOf(urlCount));
                    View viewUrl = getLayoutInflater().inflate(R.layout.layout_url_row, null);
                    ll_url_row.removeViewAt(urlCount - 1);
                    etListURL.remove(urlCount - 1);
                    etListUrlDesc.remove(urlCount - 1);
                }

                break;

            case R.id.tv_url_plus:

                if (urlCount < 5) {
                    urlCount = urlCount + 1;
                    listUrl.add("urlCount-1");
                    tv_url_count.setText(String.valueOf(urlCount));
                    View viewURL = getLayoutInflater().inflate(R.layout.layout_url_row, null);
                    ll_url_row.addView(viewURL);
                    etUrl = viewURL.findViewById(R.id.et_url);
                    ed_urlDesc = viewURL.findViewById(R.id.url_description);
                    etListURL.add(etUrl);
                    etListUrlDesc.add(ed_urlDesc);
                }

                break;

            case R.id.tv_fromDate:

                DatePickerDialog fromDateDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        Calendar fromCal = Calendar.getInstance();
                        fromCal.set(Calendar.YEAR, i);
                        fromCal.set(Calendar.MONTH, i1);
                        fromCal.set(Calendar.DAY_OF_MONTH, i2);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        fromDate = dateFormat.format(fromCal.getTime());
                        tv_fromDate.setText(fromDate);

                    }
                }, year, month, date);

                fromDateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                fromDateDialog.show();

                break;

            case R.id.tv_toDate:

                DatePickerDialog toDateDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        Calendar toCal = Calendar.getInstance();
                        toCal.set(Calendar.YEAR, i);
                        toCal.set(Calendar.MONTH, i1);
                        toCal.set(Calendar.DAY_OF_MONTH, i2);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        toDate = dateFormat.format(toCal.getTime());
                        tv_toDate.setText(toDate);

                    }
                }, year, month, date);

                toDateDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                toDateDialog.show();

                break;

            case R.id.btn_submit:

                String work_title = et_work_title.getText().toString();
                String description = et_description.getText().toString();
                if (str_class.equals(""))
                    Toast.makeText(getActivity(), "Class Is Required", Toast.LENGTH_SHORT).show();

                else if (str_subject.equals(""))
                    Toast.makeText(getActivity(), "Subject Is Required", Toast.LENGTH_SHORT).show();
                else if (work_title.equals(""))
                    Toast.makeText(getActivity(), "HomeWork Title Is Required", Toast.LENGTH_SHORT).show();

                else if (fromDate.equals(""))
                    Toast.makeText(getActivity(), "Start Date Is Required", Toast.LENGTH_SHORT).show();

                else if (toDate.equals(""))
                    Toast.makeText(getActivity(), "End Date Is Required", Toast.LENGTH_SHORT).show();

                else if (description.equals(""))
                    Toast.makeText(getActivity(), "Description Is Required", Toast.LENGTH_SHORT).show();

                else {

                    JSONObject bookJson = new JSONObject();

                    for (int i = 0; i < etListBookName.size(); i++) {

                        String bookName = etListBookName.get(i).getText().toString();
                        String pageNo = etListPageNo.get(i).getText().toString();

                        Log.d("MY_DATA_IS", " " + pageNo + " " + etListBookName.size());


                        JSONObject jsonObject = new JSONObject();

                        try {


                            jsonObject.put("book_name", bookName);
                            jsonObject.put("page_no", pageNo);

                            bookJson.put(String.valueOf(i + 1), jsonObject);


                        } catch (JSONException je) {

                        }

                        obj = new JSONObject();
                        try {
                            obj.put("Book", bookJson);
                        } catch (JSONException je) {

                        }

                        System.out.println("Book***" + obj);
                    }


                    JSONObject urlJson = new JSONObject();
                    for (int i = 0; i < etListURL.size(); i++) {

                        String url = etListURL.get(i).getText().toString();
                        String url_desc = etListUrlDesc.get(i).getText().toString();
                        Log.d("MY_DATA_IS", url);

                        JSONObject jsonObject2 = new JSONObject();

                        try {

                            jsonObject2.put("ref_url", url);
                            jsonObject2.put("url_message", url_desc);

                            urlJson.put(String.valueOf(i + 1), jsonObject2);


                        } catch (JSONException je) {

                        }

                        obj2 = new JSONObject();
                        try {
                            obj2.put("URL", urlJson);
                        } catch (JSONException je) {

                        }

                        System.out.println("URL***" + obj2);

                    }


                    JSONObject jsonImage = new JSONObject();
                    JSONObject jsonAttach = new JSONObject();
                    JSONObject obj3 = new JSONObject();


                    MultipartBody.Builder builder = new MultipartBody.Builder();
                    builder.setType(MultipartBody.FORM);

                    for (int i = 0; i < imageFilePathList.size(); i++) {


                        File f = new File(imageFilePathList.get(i));

                        try {
                            jsonImage.put("attachment_image", "file" + i);
                            jsonAttach.put(String.valueOf(i + 1), jsonImage);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        builder.addFormDataPart("file" + i, f.getName(), RequestBody.
                                create(MediaType.parse("multipart/form-data"), f));

                    }

                    try {
                        obj3.put("Attachment", jsonAttach);
                    } catch (JSONException je) {

                    }
                    Log.i("jsonHomework**", "" + jsonAttach);
                    Log.i("obj3**", "" + obj3);


                    Log.i("school_id**", "" + str_class + "*" + strSection + "*" + str_subject + et_work_title.getText().toString() +
                            tv_fromDate.getText().toString() + tv_toDate.getText().toString() + Constant.EMPLOYEE_BY_ID +
                            Constant.SCHOOL_ID);

                    builder.addFormDataPart("school_id", Constant.SCHOOL_ID);
                    builder.addFormDataPart("class", str_class);
                    builder.addFormDataPart("sections", strSection);
                    builder.addFormDataPart("subject", str_subject);
                    builder.addFormDataPart("homework_title", et_work_title.getText().toString());
                    builder.addFormDataPart("start_date", tv_fromDate.getText().toString());
                    builder.addFormDataPart("end_date", tv_toDate.getText().toString());
                    builder.addFormDataPart("added_employeeid", Constant.EMPLOYEE_BY_ID);
                    builder.addFormDataPart("description", "math homework");

                    builder.addFormDataPart("book_name", obj.toString());
                    builder.addFormDataPart("reference_url", obj2.toString());

                    builder.addFormDataPart("refrence_attachments", obj3.toString());

                    MultipartBody requestBody = builder.build();


                    mApiService.createHomework(requestBody)
                            .enqueue(new Callback<Object>() {
                                @Override
                                public void onResponse(Call<Object> call, Response<Object> response) {
                                    if (response.isSuccessful()) {
                                        Log.i("CREATE_RES**", "" + response.body());
                                        Log.i("Value***", "" + response.raw().request().url());
                                        Log.i("CREATE_RES**", "" + response.code());

                                        Toast.makeText(getActivity(), "Class " + str_class + "Section " + strSection +
                                                "Subject " + str_subject + "homework submit successfully", Toast.LENGTH_SHORT).show();
                                        spn_division.setSelection(0);
                                        spn_class.setSelection(0);
                                        spn_section.setSelection(0);
                                        listSubject.clear();
                                        mArrayUri.clear();
                                        questionbankAdapter.notifyDataSetChanged();
                                        et_work_title.setText("");
                                        et_description.setText("");
                                        tv_fromDate.setText("");
                                        tv_toDate.setText("");

                                        etBookName.setText("");
                                        etPageNo.setText("");
                                        etUrl.setText("");
                                        ed_urlDesc.setText("");


                                    } else {
                                        try {
                                            assert response.errorBody() != null;
                                            JSONObject object = new JSONObject(response.errorBody().string());
                                            String message = object.getString("message");
                                            Log.d("ADMIN_API", message);
                                            Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }


                                }

                                @Override
                                public void onFailure(Call<Object> call, Throwable t) {

                                }
                            });


                }

                break;


        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == FROM_GALLERY && resultCode == RESULT_OK
                    && null != data) {

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();
                if (data.getData() != null) {

                    Uri mImageUri = data.getData();

                    Cursor cursor = getActivity().getContentResolver().query(mImageUri,
                            filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);
                    cursor.close();


                    mArrayUri.add(mImageUri);
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImageUri);
                    String path = getRealPathFromURI((Uri) getImageUri(getActivity(), bitmap));
                    imageFilePathList.add(path);
                    File f = new File(path);
                    System.out.println("mArrayUri**1 " + mArrayUri.size() + "**" + mArrayUri.get(0).getPath());
                    setRecyclerViewForDialog(mArrayUri);


                } else {
                    if (data.getClipData() != null) {
                        ClipData mClipData = data.getClipData();


                        if (mClipData.getItemCount() <= 8) {
                            for (int i = 0; i < mClipData.getItemCount(); i++) {

                                ClipData.Item item = mClipData.getItemAt(i);
                                Uri uri = item.getUri();


                                mArrayUri.add(uri);

                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                                String path = getRealPathFromURI((Uri) getImageUri(getActivity(), bitmap));
                                imageFilePathList.add(path);
                                // Get the cursor
                                Cursor cursor = getActivity().getContentResolver().query(uri, filePathColumn, null, null, null);
                                // Move to first row
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                imageEncoded = cursor.getString(columnIndex);
                                imagesEncodedList.add(imageEncoded);
                                cursor.close();

                            }
                            System.out.println("mArrayUri**2 " + mArrayUri.size() + "**" + mArrayUri.get(0).getPath());
                            setRecyclerViewForDialog(mArrayUri);


                        } else {
                            Toast.makeText(getActivity(), "You can't select images more than 8", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            } else {
                Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private Object getImageUri(Context context, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private void setRecyclerViewForDialog(ArrayList<Uri> mArrayUri) {
        questionbankAdapter = new GalleryAdapter(mArrayUri, getActivity(), Constant.RV_QUESTION_BANK);
        rv_attachment.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rv_attachment.setAdapter(questionbankAdapter);

    }


    private void crateHomeworkAPI() {



      /*


                   mApiService.createHomework(Constant.SCHOOL_ID,str_class,str_subject,et_work_title.getText().toString(),
                           tv_fromDate.getText().toString(),tv_toDate.getText().toString(),
                           et_description.getText().toString(), strSection,Constant.EMPLOYEE_BY_ID,obj,obj2,obj3)
       */
        //  RequestBody bookRequestBody = RequestBody.create(MediaType.parse("image/*"), bookFile);

        //   MultipartBody.Part bookBodyPart = MultipartBody.Part.createFormData("book_image ", bookFile.getName(), bookRequestBody);


  /*    RequestBody bookRequestBody = RequestBody.create(MediaType.parse("image/*"), bookFile);

        MultipartBody.Part bookBodyPart = MultipartBody.Part.createFormData("book_image ", bookFile.getName(), bookRequestBody);
*/


    }


}




























/*
    private void setSpinnerForSubject() {

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Select Class");
        arrayList.add("1");
        arrayList.add("2");
        arrayList.add("3");
        arrayList.add("4");

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getActivity(), arrayList, "Blue");
        spn_class.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        spn_class.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0)
                    str_class = "";
                else
                    str_class = spn_class.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void setSpinnerForClass() {

        ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Select Subject");
        arrayList.add("English");
        arrayList.add("Hindi");
        arrayList.add("Math");
        arrayList.add("Science");

        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(getActivity(), arrayList, "Blue");
        spn_Subject.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        spn_Subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i == 0)
                    str_subject = "";
                else
                    str_subject = spn_Subject.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }*/

