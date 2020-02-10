package in.varadhismartek.patashalaerp.DashboardModule.EmployeeModule;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import in.varadhismartek.Utils.Constant;
import in.varadhismartek.patashalaerp.GeneralClass.CircleImageView;
import in.varadhismartek.patashalaerp.R;
import in.varadhismartek.patashalaerp.Retrofit.APIService;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtils;
import in.varadhismartek.patashalaerp.Retrofit.ApiUtilsPatashala;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_EmployeeView extends Fragment implements View.OnClickListener {
    TextView tv_attendance, tvTitle,tvNoRecords;
    LinearLayout ll_employee;
    String empID, FName, LName, AdhaarNo,PhoneNo;
    APIService mApiService;


    TextView tv_firstName, tv_middleName, tv_lastName, tv_gender, tv_blood, tv_caste, tv_birthPlace, tv_dob, tv_nationality, tv_doJoin, tv_married, tv_childStatus,
            tv_mobile, tv_email;
    TextView tv_fatherName, tv_fatherOcc, tv_motherName, tv_motherOcc, tv_spouseName, tv_spouseOcc, tv_guardName, tv_guardOcc;
    TextView tv_address1, tv_landmark1, tv_contact1, tv_address2, tv_landmark2, tv_contact2;
    TextView tv_schoolNameX, tv_schoolBoardX, tv_schoolPercentX, tv_schoolPassingX, tv_schoolStateX;
    TextView tv_schoolNameXII, tv_schoolBoardXII, tv_schoolPercentXII, tv_schoolPassingXII, tv_schoolStateXII;
    TextView tv_ugUniversityName, tv_ugCollageName, tv_ugName, tv_ugBranch, tv_ugPercent, tv_ugPassingYear;
    TextView tv_pgUniversity, tv_pgCollage, tv_pgName, tv_pgBranch, tv_pgPercent, tv_pgPassingYear;
    TextView tv_phdUniversity, tv_phdCollage, tv_phdName, tv_phdBranch, tv_phdPercent, tv_phdPassingYear;
    TextView tv_bedUniversity, tv_bedCollage, tv_bedName, tv_bedBranch, tv_bedPercent, tv_bedPassingYear;
    TextView tv_height, tv_weight, tv_shortEye, tv_longEye, tv_colorVision, tv_commentEye, tv_teethProb, tv_commentTeeth, tv_rightEarF,
            tv_leftEarF, tv_hearingDiff, tv_commentEar, tv_anyDis, tv_commentDisability;

    RecyclerView recycler_view, rv_docs, rv_achievement, rv_sports, rv_child;
    ImageView iv_backBtn;
    CircleImageView civ_image;
    TextView tv_name, tv_id, tv_dept, tv_role, tv_className, tv_sectionName;
    LinearLayout ll_rowDarkStaff, ll_rowDarkParent, ll_staffPersonal, ll_healthStudent, ll_addressStaff,
            ll_spouse, ll_guard;
    LinearLayout ll_personal, ll_academic, ll_health, ll_docs;
    LinearLayout ll_personal_view, ll_academic_view, ll_health_view, ll_docs_view;

    APIService mApiServicePatashala;
    ProgressDialog progressDialog;
    ProfileAdapter adapter;
    ProfileAdapter adapterDocs;
    ProfileAdapter adapterAchievement;
    ProfileAdapter adapterSports;
    ProfileAdapter adapterChild;
    ArrayList<ProfileModel> workExpList;
    ArrayList<ProfileModel> docsList;
    ArrayList<ProfileModel> achivementList;
    ArrayList<ProfileModel> sportsList;
    ArrayList<ProfileModel> childList;
TextView tvLeave,tv_homework,tv_account;
    public Fragment_EmployeeView() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_employee_view, container, false);

        initViews(view);
        initListener();

        gettingEmployeePersonalDetailsAPI();

        setRecyclerView();
        setRecyclerViewDocs();
        setRecyclerViewSports();
        setRecyclerViewAchievement();
        setRecyclerViewChild();
        return view;
    }
    private void setRecyclerView() {

        adapter = new ProfileAdapter(workExpList, getActivity(), Constant.RV_PROFILE_WORK_EXPERIENCE);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler_view.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setRecyclerViewDocs() {

        adapterDocs = new ProfileAdapter(docsList, getActivity(), Constant.RV_PROFILE_DOCUMENTS);
        rv_docs.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_docs.setAdapter(adapterDocs);
        adapterDocs.notifyDataSetChanged();
    }
    private void setRecyclerViewAchievement() {

        adapterAchievement = new ProfileAdapter(achivementList, getActivity(), Constant.RV_PROFILE_ACHIEVEMENT);
        rv_achievement.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_achievement.setAdapter(adapterAchievement);
        adapterAchievement.notifyDataSetChanged();
    }

    private void setRecyclerViewSports() {

        adapterSports = new ProfileAdapter(sportsList, getActivity(), Constant.RV_PROFILE_SPORTS_ROW);
        rv_sports.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_sports.setAdapter(adapterSports);
        adapterSports.notifyDataSetChanged();
    }

    private void setRecyclerViewChild() {

        adapterChild = new ProfileAdapter(childList, getActivity(), Constant.RV_PROFILE_CHILDREN);
        rv_child.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_child.setAdapter(adapterChild);
        adapterChild.notifyDataSetChanged();
    }

    private void initListener() {
        ll_academic.setOnClickListener(this);
        ll_personal.setOnClickListener(this);
        iv_backBtn.setOnClickListener(this);
        tv_attendance.setOnClickListener(this);
        ll_health.setOnClickListener(this);
        ll_docs.setOnClickListener(this);
        tvLeave.setOnClickListener(this);
        tv_homework.setOnClickListener(this);
        tv_account.setOnClickListener(this);
    }


    private void gettingEmployeePersonalDetailsAPI() {

        //   progressDialog.show();

        mApiService.gettingEmployeePersonalDetails(Constant.SCHOOL_ID, FName, LName,
                PhoneNo, AdhaarNo).enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {

                if (response.isSuccessful()) {

                    try {

                        tvNoRecords.setVisibility(View.GONE);
                        ll_employee.setVisibility(View.VISIBLE);

                        JSONObject object = new JSONObject(new Gson().toJson(response.body()));
                        String status = object.getString("status");
                        String message = object.getString("message");

                        if (status.equalsIgnoreCase("Success")) {

                            JSONObject jsonData = object.getJSONObject("data");
                            JSONObject empData = jsonData.getJSONObject("employee_data");

                            String employee_uuid = empData.getString("employee_uuid");
                            String passport_pic_back = empData.getString("passport_pic_back");
                            String fathers_middle_name = empData.getString("fathers_middle_name");
                            String driving_license_pic_front = empData.getString("driving_license_pic_front");
                            String marital_status = empData.getString("marital_status");
                            String mothers_organisation = empData.getString("mothers_organisation");
                            String community = empData.getString("community");
                            String department = empData.getString("department");
                            System.out.println("department*****"+department);
                            String mothers_last_name = empData.getString("mothers_last_name");
                            String fathers_last_name = empData.getString("fathers_last_name");
                            String spouse_designation = empData.getString("spouse_designation");
                            String mothers_first_name = empData.getString("mothers_first_name");
                            String blood_group = empData.getString("blood_group");
                            String spouse_email = empData.getString("spouse_email");
                            String weight = empData.getString("weight");
                            String present_locality_name = empData.getString("present_locality_name");
                            //String approved_by = empData.getString("approved_by");
                            String wing = empData.getString("wing");
                            String spouse_phone_no = empData.getString("spouse_phone_no");
                            String pan_card_no = empData.getString("pan_card_no");
                            String spouse_last_name = empData.getString("spouse_last_name");
                            String present_state = empData.getString("present_state");
                            String fathers_designation = empData.getString("fathers_designation");
                            String present_street = empData.getString("present_street");
                            String driving_license_pic_back = empData.getString("driving_license_pic_back");
                            String permanent_contact_number = empData.getString("permanent_contact_number");
                            String child_status = empData.getString("child_status");
                            //String deleted_datetime = empData.getString("deleted_datetime");
                            String adhaar_card_pic_back = empData.getString("adhaar_card_pic_back");
                            String voter_id_pic_back = empData.getString("voter_id_pic_back");
                            String adhaar_card_no = empData.getString("adhaar_card_no");
                            String present_country = empData.getString("present_country");
                            //String updated_timestamp = empData.getString("updated_timestamp");
                            //String number_of_childrens = empData.getString("number_of_childrens");
                            String pan_card_pic_front = empData.getString("pan_card_pic_front");
                            String permanent_door_no = empData.getString("permanent_door_no");
                            String permanent_pincode = empData.getString("permanent_pincode");
                            String present_pincode = empData.getString("present_pincode");
                            String nationality = empData.getString("nationality");
                            //String approved_timestamp = empData.getString("approved_timestamp");
                            String short_eye_vision = empData.getString("short_eye_vision");
                            String spouse_organisation = empData.getString("spouse_organisation");
                            String permanent_landmark_name = empData.getString("permanent_landmark_name");
                            String dob = empData.getString("dob");
                            String role = empData.getString("role");
                            String marriage_date = empData.getString("marriage_date");
                            String present_door_no = empData.getString("present_door_no");
                            String driving_license_no = empData.getString("driving_license_no");
                            String spouse_first_name = empData.getString("spouse_first_name");
                            String long_eye_vision = empData.getString("long_eye_vision");
                            String voter_id_pic_front = empData.getString("voter_id_pic_front");
                            //String division = empData.getString("division");
                            String birth_place = empData.getString("birth_place");
                            String pan_card_pic_back = empData.getString("pan_card_pic_back");
                            //String delete_message = empData.getString("delete_message");
                            String middle_name = empData.getString("middle_name");
                            //String mothers_email = empData.getString("mothers_email");
                            //String personal_doctor_mobile_number = empData.getString("personal_doctor_mobile_number");
                            String passport_pic_front = empData.getString("passport_pic_front");
                            String sex = empData.getString("sex");
                            //String subject_id = empData.getString("subject_id");
                            String mothers_mobile_no = empData.getString("mothers_mobile_no");
                            //String custom_employee_id = empData.getString("custom_employee_id");
                            String email = empData.getString("email");
                            String fathers_occupation = empData.getString("fathers_occupation");
                            String first_name = empData.getString("first_name");
                            String last_name = empData.getString("last_name");
                            String spouse_occupation = empData.getString("spouse_occupation");
                            String adhaar_card_pic_front = empData.getString("adhaar_card_pic_front");
                            String present_landmark_name = empData.getString("present_landmark_name");
                            //String mothers_designation = empData.getString("mothers_designation");
                            //String fathers_email = empData.getString("fathers_email");
                            //String updated_by = empData.getString("updated_by");
                            String caste = empData.getString("caste");
                            //String submitted_by = empData.getString("submitted_by");
                            //String submitted_timestamp = empData.getString("submitted_timestamp");
                            String present_contact_number = empData.getString("present_contact_number");
                            String permanent_city = empData.getString("permanent_city");
                            String voter_id_no = empData.getString("voter_id_no");
                            String permanent_country = empData.getString("permanent_country");
                            //String mothers_middle_name = empData.getString("mothers_middle_name");
                            String height = empData.getString("height");
                            //String fathers_organisation = empData.getString("fathers_organisation");
                            String fathers_first_name = empData.getString("fathers_first_name");
                            //String medical_issue = empData.getString("medical_issue");
                            //String fathers_mobile_no = empData.getString("fathers_mobile_no");
                            //String employee_status = empData.getString("employee_status");
                            String phone_number = empData.getString("phone_number");
                            //String employee_deleted = empData.getString("employee_deleted");

                            if (!empData.isNull("date_of_joining")) {
                                String date_of_joining = empData.getString("date_of_joining");
                                //   tv_doJoin.setText(date_of_joining);
                            }
                            String permanent_street = empData.getString("permanent_street");
                            String mothers_occupation = empData.getString("mothers_occupation");
                            String present_city = empData.getString("present_city");
                            String permanent_locality_name = empData.getString("permanent_locality_name");
                            String passport_no = empData.getString("passport_no");
                            //String school_id = empData.getString("school_id");
                            String permanent_state = empData.getString("permanent_state");
                            //String spouse_middle_name = empData.getString("spouse_middle_name");
                            String employee_photo = empData.getString("employee_photo");

                            Log.d("Profile_api_success", response.code() + " " + message);

                            docsList.add(new ProfileModel("Adhar Card",
                                    adhaar_card_no, adhaar_card_pic_back, adhaar_card_pic_front));
                            docsList.add(new ProfileModel("Voter Card",
                                    voter_id_no, voter_id_pic_back, voter_id_pic_front));
                            docsList.add(new ProfileModel("Pan Card",
                                    pan_card_no, pan_card_pic_back, pan_card_pic_front));
                            docsList.add(new ProfileModel("Driving Licence",
                                    driving_license_no, driving_license_pic_back, driving_license_pic_front));
                            docsList.add(new ProfileModel("Passport Card",
                                    passport_no, passport_pic_back, passport_pic_front));

                            adapterDocs.notifyDataSetChanged();

                            tv_name.setText(first_name + " " + last_name);
                            tv_id.setText(employee_uuid);
                            tv_dept.setText(department);
                            tv_role.setText(role);

                            tv_firstName.setText(first_name);
                            tv_middleName.setText(middle_name);
                            tv_lastName.setText(last_name);
                            tv_gender.setText(sex);
                            tv_blood.setText(blood_group);
                            tv_caste.setText(caste);
                            tv_birthPlace.setText(birth_place);
                            tv_dob.setText(dob);
                            tv_nationality.setText(nationality);
                            tv_married.setText(marital_status);
                            tv_childStatus.setText(child_status);
                            tv_mobile.setText(phone_number);
                            tv_email.setText(email);

                            tv_fatherName.setText(fathers_first_name + " " + fathers_last_name);
                            tv_fatherOcc.setText(fathers_occupation);
                            tv_motherName.setText(mothers_first_name + " " + mothers_last_name);
                            tv_motherOcc.setText(mothers_occupation);
                            tv_spouseName.setText(spouse_first_name + " " + spouse_last_name);
                            tv_spouseOcc.setText(spouse_occupation);

                            tv_address1.setText(present_door_no + ", " + present_street + ", " + present_locality_name + ", " + present_city + ", " + present_state +
                                    ", " + present_country + "-" + present_pincode);
                            tv_landmark1.setText(present_landmark_name);
                            tv_contact1.setText(present_contact_number);
                            tv_address2.setText(permanent_door_no + ", " + permanent_street + ", " + permanent_locality_name + ", " + permanent_city + ", " + permanent_state +
                                    ", " + permanent_country + "-" + permanent_pincode);
                            tv_landmark2.setText(permanent_landmark_name);
                            tv_contact2.setText(permanent_contact_number);

                            tv_height.setText(height);
                            tv_weight.setText(weight);
                            tv_shortEye.setText(short_eye_vision);
                            tv_longEye.setText(long_eye_vision);

                            final String imageUrlg = "http://13.233.109.165:8000/media/";
                            assert getActivity() != null;
                            Glide.with(getActivity()).load(imageUrlg + employee_photo).into(civ_image);

                            JSONArray jsonArray = jsonData.getJSONArray("childeren_details");

                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject childObject = jsonArray.getJSONObject(i);

                                //String no_of_child = childObject.getString("no_of_child");
                                //String employee_id = childObject.getString("employee_id");
                                //String child_school_college_city = childObject.getString("child_school_college_city");
                                //String child_organization_role = childObject.getString("child_organization_role");
                                //String child_occupation = childObject.getString("child_occupation");
                                //String id = childObject.getString("id");
                                //String child_organization_name = childObject.getString("child_organization_name");
                                String child_DOB = childObject.getString("child_DOB");
                                String child_name = childObject.getString("child_name");
                                //String child_school_college_name = childObject.getString("child_school_college_name");
                                //String child_organization_city = childObject.getString("child_organization_city");
                                //String child_class_course_name = childObject.getString("child_class_course_name");

                                    childList.add(new ProfileModel(child_name, child_DOB));

                            }

                               adapterChild.notifyDataSetChanged();

                            Log.d("Profile_api_success", response.code() + " " + message);
                            //   progressDialog.dismiss();


                        } else {
                            Log.d("Profile_api_else", response.code() + " " + message);
                            Toast.makeText(getActivity(), response.code() + " " + message, Toast.LENGTH_SHORT).show();
                            //    progressDialog.dismiss();

                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    try {
                        assert response.errorBody() != null;
                        JSONObject object = new JSONObject(response.errorBody().string());
                        String message = object.getString("message");
                        Log.d("ADMIN_API", message);
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        tvNoRecords.setVisibility(View.VISIBLE);
                        ll_employee.setVisibility(View.GONE);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {

                Log.d("safpjsaiofi", t.getMessage());
            }
        });

    }


    private void initViews(View view) {
        mApiService = ApiUtils.getAPIService();
        tv_attendance = view.findViewById(R.id.tv_attendance);
        tvLeave = view.findViewById(R.id.tv_leave);
        tv_homework = view.findViewById(R.id.tv_homework);
        ll_employee = view.findViewById(R.id.ll_employee);
        tvNoRecords = view.findViewById(R.id.tvNorecords);
        tv_account = view.findViewById(R.id.tv_account);
        // tvTitle.setText("Employee Profile ");

        mApiServicePatashala = ApiUtilsPatashala.getService();
        //progressDialog = new ProgressDialog(getActivity());
        //progressDialog.setMessage("Please Wait");

        workExpList = new ArrayList<>();
        docsList = new ArrayList<>();
        achivementList = new ArrayList<>();
        sportsList = new ArrayList<>();
        childList = new ArrayList<>();

        recycler_view = view.findViewById(R.id.recycler_view);
        rv_docs = view.findViewById(R.id.rv_docs);
         rv_achievement = view.findViewById(R.id.rv_achievement);
          rv_sports = view.findViewById(R.id.rv_sports);
        rv_child = view.findViewById(R.id.rv_child);

        iv_backBtn = view.findViewById(R.id.iv_backBtn);
        civ_image = view.findViewById(R.id.civ_image);
        tv_name = view.findViewById(R.id.tv_name);
        tv_id = view.findViewById(R.id.tv_id);

        ll_rowDarkStaff = view.findViewById(R.id.ll_rowDarkStaff);
        ll_rowDarkParent = view.findViewById(R.id.ll_rowDarkParent);
          ll_healthStudent = view.findViewById(R.id.ll_healthStudent);
        ll_staffPersonal = view.findViewById(R.id.ll_staffPersonal);
        ll_addressStaff = view.findViewById(R.id.ll_addressStaff);
        ll_spouse = view.findViewById(R.id.ll_spouse);
        ll_guard = view.findViewById(R.id.ll_guard);

        tv_dept = view.findViewById(R.id.tv_dept);
        tv_role = view.findViewById(R.id.tv_role);
        tv_className = view.findViewById(R.id.tv_className);
        tv_sectionName = view.findViewById(R.id.tv_sectionName);

        ll_personal = view.findViewById(R.id.ll_personal);
        ll_academic = view.findViewById(R.id.ll_academic);
        ll_health = view.findViewById(R.id.ll_health);
        ll_docs = view.findViewById(R.id.ll_docs);
        ll_personal_view = view.findViewById(R.id.ll_personal_view);
        ll_academic_view = view.findViewById(R.id.ll_academic_view);
        ll_health_view = view.findViewById(R.id.ll_health_view);
        ll_docs_view = view.findViewById(R.id.ll_docs_view);

        tv_firstName = view.findViewById(R.id.tv_firstName);
        tv_middleName = view.findViewById(R.id.tv_middleName);
        tv_lastName = view.findViewById(R.id.tv_lastName);
        tv_gender = view.findViewById(R.id.tv_gender);
        tv_blood = view.findViewById(R.id.tv_blood);
        tv_caste = view.findViewById(R.id.tv_caste);
        tv_birthPlace = view.findViewById(R.id.tv_birthPlace);
        tv_dob = view.findViewById(R.id.tv_dob);
        tv_nationality = view.findViewById(R.id.tv_nationality);
        tv_doJoin = view.findViewById(R.id.tv_doJoin);
        tv_married = view.findViewById(R.id.tv_married);
        tv_childStatus = view.findViewById(R.id.tv_childStatus);
        tv_mobile = view.findViewById(R.id.tv_mobile);
        tv_email = view.findViewById(R.id.tv_email);

        tv_fatherName = view.findViewById(R.id.tv_fatherName);
        tv_fatherOcc = view.findViewById(R.id.tv_fatherOcc);
        tv_motherName = view.findViewById(R.id.tv_motherName);
        tv_motherOcc = view.findViewById(R.id.tv_motherOcc);
        tv_spouseName = view.findViewById(R.id.tv_spouseName);
        tv_spouseOcc = view.findViewById(R.id.tv_spouseOcc);
        tv_guardName = view.findViewById(R.id.tv_guardName);
        tv_guardOcc = view.findViewById(R.id.tv_guardOcc);

        tv_address1 = view.findViewById(R.id.tv_address1);
        tv_landmark1 = view.findViewById(R.id.tv_landmark1);
        tv_contact1 = view.findViewById(R.id.tv_contact1);
        tv_address2 = view.findViewById(R.id.tv_address2);
        tv_landmark2 = view.findViewById(R.id.tv_landmark2);
        tv_contact2 = view.findViewById(R.id.tv_contact2);

        tv_schoolNameX = view.findViewById(R.id.tv_schoolNameX);
        tv_schoolBoardX = view.findViewById(R.id.tv_schoolBoardX);
        tv_schoolPercentX = view.findViewById(R.id.tv_schoolPercentX);
        tv_schoolPassingX = view.findViewById(R.id.tv_schoolPassingX);
        tv_schoolStateX = view.findViewById(R.id.tv_schoolStateX);

        tv_schoolNameXII = view.findViewById(R.id.tv_schoolNameXII);
        tv_schoolBoardXII = view.findViewById(R.id.tv_schoolBoardXII);
        tv_schoolPercentXII = view.findViewById(R.id.tv_schoolPercentXII);
        tv_schoolPassingXII = view.findViewById(R.id.tv_schoolPassingXII);
        tv_schoolStateXII = view.findViewById(R.id.tv_schoolStateXII);

        tv_ugUniversityName = view.findViewById(R.id.tv_ugUniversityName);
        tv_ugCollageName = view.findViewById(R.id.tv_ugCollageName);
        tv_ugName = view.findViewById(R.id.tv_ugName);
        tv_ugBranch = view.findViewById(R.id.tv_ugBranch);
        tv_ugPercent = view.findViewById(R.id.tv_ugPercent);
        tv_ugPassingYear = view.findViewById(R.id.tv_ugPassingYear);

        tv_pgUniversity = view.findViewById(R.id.tv_pgUniversity);
        tv_pgCollage = view.findViewById(R.id.tv_pgCollage);
        tv_pgName = view.findViewById(R.id.tv_pgName);
        tv_pgBranch = view.findViewById(R.id.tv_pgBranch);
        tv_pgPercent = view.findViewById(R.id.tv_pgPercent);
        tv_pgPassingYear = view.findViewById(R.id.tv_pgPassingYear);

        tv_phdUniversity = view.findViewById(R.id.tv_phdUniversity);
        tv_phdCollage = view.findViewById(R.id.tv_phdCollage);
        tv_phdName = view.findViewById(R.id.tv_phdName);
        tv_phdBranch = view.findViewById(R.id.tv_phdBranch);
        tv_phdPercent = view.findViewById(R.id.tv_phdPercent);
        tv_phdPassingYear = view.findViewById(R.id.tv_phdPassingYear);

        tv_bedUniversity = view.findViewById(R.id.tv_bedUniversity);
        tv_bedCollage = view.findViewById(R.id.tv_bedCollage);
        tv_bedName = view.findViewById(R.id.tv_bedName);
        tv_bedBranch = view.findViewById(R.id.tv_bedBranch);
        tv_bedPercent = view.findViewById(R.id.tv_bedPercent);
        tv_bedPassingYear = view.findViewById(R.id.tv_bedPassingYear);

        tv_height = view.findViewById(R.id.tv_height);
        tv_weight = view.findViewById(R.id.tv_weight);
        tv_shortEye = view.findViewById(R.id.tv_shortEye);
        tv_longEye = view.findViewById(R.id.tv_longEye);
        tv_colorVision = view.findViewById(R.id.tv_colorVision);
        tv_commentEye = view.findViewById(R.id.tv_commentEye);
        tv_teethProb = view.findViewById(R.id.tv_teethProb);
        tv_commentTeeth = view.findViewById(R.id.tv_commentTeeth);
        tv_rightEarF = view.findViewById(R.id.tv_rightEarF);
        tv_leftEarF = view.findViewById(R.id.tv_leftEarF);
        tv_hearingDiff = view.findViewById(R.id.tv_hearingDiff);
        tv_commentEar = view.findViewById(R.id.tv_commentEar);
        tv_anyDis = view.findViewById(R.id.tv_anyDis);
        tv_commentDisability = view.findViewById(R.id.tv_commentDisability);

        Bundle b = getArguments();
        if (b != null) {
            empID = b.getString("EMPUUID");
            FName = b.getString("FNAME");
            LName = b.getString("LNAME");
            AdhaarNo = b.getString("ADHAARNO");
            PhoneNo = b.getString("PHONENO");
        }
        Log.i("fragmentType**88", "" + empID + "**"+FName+"***" + LName + "*" + AdhaarNo+"***"+PhoneNo);

    }

    @Override
    public void onClick(View v) {
        EmployeeActivity employeeActivity = (EmployeeActivity) getActivity();
        Bundle bundle;
        switch (v.getId()) {
            case R.id.tv_account:
                tv_account.setBackgroundResource(R.drawable.border_rect);
                tvLeave.setBackgroundResource(R.drawable.border);
                tv_homework.setBackgroundResource(R.drawable.border);
                tv_attendance.setBackgroundResource(R.drawable.border);
                ll_academic.setBackgroundResource(R.drawable.border);
                ll_health.setBackgroundResource(R.drawable.border);
                ll_docs.setBackgroundResource(R.drawable.border);
                ll_health_view.setVisibility(View.GONE);
                ll_docs_view.setVisibility(View.GONE);
                ll_personal_view.setVisibility(View.GONE);
                tv_attendance.setVisibility(View.VISIBLE);

                 bundle = new Bundle();
                bundle.putString("EMPUUID", empID);
                bundle.putString("EMPLOYEE", "EMP_PAT_LEAVE");
                employeeActivity.loadFragment(Constant.EMPLOYEE_ACCOUNT_DETAILS, bundle);
                break;

                case R.id.tv_leave:
                tvLeave.setBackgroundResource(R.drawable.border_rect);
                    tv_account.setBackgroundResource(R.drawable.border);
                tv_homework.setBackgroundResource(R.drawable.border);
                tv_attendance.setBackgroundResource(R.drawable.border);
                ll_academic.setBackgroundResource(R.drawable.border);
                ll_health.setBackgroundResource(R.drawable.border);
                ll_docs.setBackgroundResource(R.drawable.border);
                ll_health_view.setVisibility(View.GONE);
                ll_docs_view.setVisibility(View.GONE);
                ll_personal_view.setVisibility(View.GONE);
                tv_attendance.setVisibility(View.VISIBLE);

                 bundle = new Bundle();
                bundle.putString("EMPUUID", empID);
                bundle.putString("EMPLOYEE", "EMP_PAT_LEAVE");
                employeeActivity.loadFragment(Constant.EMPLOYEE_LEAVE, bundle);
                break;

                case R.id.tv_homework:
                tv_homework.setBackgroundResource(R.drawable.border_rect);
                tvLeave.setBackgroundResource(R.drawable.border);
                    tv_account.setBackgroundResource(R.drawable.border);
                tv_attendance.setBackgroundResource(R.drawable.border);
                ll_academic.setBackgroundResource(R.drawable.border);
                ll_health.setBackgroundResource(R.drawable.border);
                ll_docs.setBackgroundResource(R.drawable.border);

                ll_health_view.setVisibility(View.GONE);
                ll_docs_view.setVisibility(View.GONE);
                ll_personal_view.setVisibility(View.GONE);
                tv_attendance.setVisibility(View.VISIBLE);

                bundle = new Bundle();
                bundle.putString("EMPUUID", empID);
                bundle.putString("STAFF", "EMPLOYEE");
                employeeActivity.loadFragment(Constant.EMPLOYEE_HOMEWORK, bundle);
                break;


            case R.id.tv_attendance:
                tv_attendance.setBackgroundResource(R.drawable.border_rect);
                tv_account.setBackgroundResource(R.drawable.border);
                ll_academic.setBackgroundResource(R.drawable.border);
                tv_homework.setBackgroundResource(R.drawable.border);
                ll_health.setBackgroundResource(R.drawable.border);
                ll_docs.setBackgroundResource(R.drawable.border);
                ll_health_view.setVisibility(View.GONE);
                ll_docs_view.setVisibility(View.GONE);
                ll_personal_view.setVisibility(View.GONE);
                tv_attendance.setVisibility(View.VISIBLE);
               // EmployeeActivity employeeActivity = (EmployeeActivity) getActivity();
                 bundle = new Bundle();
                bundle.putString("EMPUUID", empID);
                bundle.putString("FNAME", FName);
                bundle.putString("LNAME", LName);
                Constant.audience_type="";
                employeeActivity.loadFragment(Constant.ATTENDANCE_FRAGMENT, bundle);

                break;
            case R.id.iv_backBtn:
                getActivity().onBackPressed();
                break;

            case R.id.ll_personal:
                ll_personal.setBackgroundResource(R.drawable.border_rect);
                tv_account.setBackgroundResource(R.drawable.border);
                tv_homework.setBackgroundResource(R.drawable.border);
                tv_attendance.setBackgroundResource(R.drawable.border);
                ll_academic.setBackgroundResource(R.drawable.border);
                ll_health.setBackgroundResource(R.drawable.border);
                ll_docs.setBackgroundResource(R.drawable.border);
                ll_personal_view.setVisibility(View.VISIBLE);
                ll_academic_view.setVisibility(View.GONE);
                ll_health_view.setVisibility(View.GONE);
                ll_docs_view.setVisibility(View.GONE);

               /* if (Constant.audience_type.equalsIgnoreCase("Parent"))
                    ll_academic_view.setVisibility(View.GONE);
*/

                break;

            case R.id.ll_academic:

                ll_personal.setBackgroundResource(R.drawable.border);
                tv_account.setBackgroundResource(R.drawable.border);
                ll_academic.setBackgroundResource(R.drawable.border_rect);
                tv_attendance.setBackgroundResource(R.drawable.border);
                tv_homework.setBackgroundResource(R.drawable.border);
                ll_health.setBackgroundResource(R.drawable.border);
                ll_docs.setBackgroundResource(R.drawable.border);
                ll_personal_view.setVisibility(View.GONE);
                ll_academic_view.setVisibility(View.VISIBLE);
                ll_health_view.setVisibility(View.GONE);
                ll_docs_view.setVisibility(View.GONE);
                /*if (Constant.audience_type.equalsIgnoreCase("Parent"))
                    ll_academic_view.setVisibility(View.GONE);*/
                break;

            case R.id.ll_health:
                tv_attendance.setBackgroundResource(R.drawable.border);
                tv_account.setBackgroundResource(R.drawable.border);
                tv_homework.setBackgroundResource(R.drawable.border);
                ll_personal.setBackgroundResource(R.drawable.border);
                ll_academic.setBackgroundResource(R.drawable.border);
                ll_health.setBackgroundResource(R.drawable.border_rect);
                ll_docs.setBackgroundResource(R.drawable.border);
                ll_personal_view.setVisibility(View.GONE);
                ll_academic_view.setVisibility(View.GONE);
                ll_health_view.setVisibility(View.VISIBLE);
                ll_docs_view.setVisibility(View.GONE);
                /*if (Constant.audience_type.equalsIgnoreCase("Parent"))
                    ll_academic_view.setVisibility(View.GONE);*/
                break;

            case R.id.ll_docs:
                tv_attendance.setBackgroundResource(R.drawable.border);
                tv_account.setBackgroundResource(R.drawable.border);
                ll_personal.setBackgroundResource(R.drawable.border);
                tv_homework.setBackgroundResource(R.drawable.border);
                ll_academic.setBackgroundResource(R.drawable.border);
                ll_health.setBackgroundResource(R.drawable.border);
                ll_docs.setBackgroundResource(R.drawable.border_rect);
                ll_personal_view.setVisibility(View.GONE);
                ll_academic_view.setVisibility(View.GONE);
                ll_health_view.setVisibility(View.GONE);
                ll_docs_view.setVisibility(View.VISIBLE);
               /* if (Constant.audience_type.equalsIgnoreCase("Parent"))
                    ll_academic_view.setVisibility(View.GONE);*/
                break;
        }
    }


}
