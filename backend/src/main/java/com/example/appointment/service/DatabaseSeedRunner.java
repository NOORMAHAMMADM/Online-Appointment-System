package com.example.appointment.service;

import com.example.appointment.entity.Category;
import com.example.appointment.entity.Provider;
import com.example.appointment.entity.Schedule;
import com.example.appointment.repository.CategoryRepository;
import com.example.appointment.repository.ProviderRepository;
import com.example.appointment.repository.ScheduleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@Order(1)
public class DatabaseSeedRunner implements ApplicationRunner {

    private final CategoryRepository categoryRepository;
    private final ProviderRepository providerRepository;
    private final ScheduleRepository scheduleRepository;

    public DatabaseSeedRunner(
            CategoryRepository categoryRepository,
            ProviderRepository providerRepository,
            ScheduleRepository scheduleRepository) {
        this.categoryRepository = categoryRepository;
        this.providerRepository = providerRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (categoryRepository.count() == 0) {
            seedCategories();
        }

        if (providerRepository.count() == 0) {
            List<Provider> savedProviders = seedProviders();
            if (scheduleRepository.count() == 0 && !savedProviders.isEmpty()) {
                seedSchedules(savedProviders);
            }
        }
    }

    private void seedCategories() {
        List<Category> categories = List.of(
                new Category("Hospitals", "Book appointments with top hospitals, doctors, specialists, and healthcare staff."),
                new Category("Schools", "Schedule meetings with principals, HODs, teachers, and school administration."),
                new Category("Government Offices", "Connect with government officers, department heads, and public service staff."),
                new Category("Business Services", "Book sessions with CEOs, managers, business consultants, and executives."),
                new Category("Public Representatives", "Schedule consultations with MLAs, MPs, municipal representatives, and public officials."),
                new Category("Entertainment", "Connect with actors, directors, producers, and creative artists.")
        );
        categoryRepository.saveAll(categories);
    }

    private List<Provider> seedProviders() {
        List<Provider> providers = new ArrayList<>();
        int counter = 1;

        // 1. Hospitals (categoryId = 1)
        String[] hospitals = {"Apollo Hospital", "Yashoda Hospital", "Care Hospital"};
        String[] doctorSpecs = {"General Medicine", "Cardiology", "Dermatology", "Neurology", "Orthopedics", "Pediatrics", "ENT", "Gynecology", "Ophthalmology"};
        String[] nurseSpecs = {"General Nursing", "ICU Nursing", "Emergency Nursing", "Pediatric Nursing", "Surgical Nursing"};
        String[] doctorNames = {"Dr. Aarav Sharma", "Dr. Priya Nair", "Dr. Rohan Mehta", "Dr. Sneha Reddy", "Dr. Vikram Rao", "Dr. Ananya Verma", "Dr. Karthik Iyer", "Dr. Meera Joshi", "Dr. Siddharth Malhotra"};

        for (String hospital : hospitals) {
            for (int i = 0; i < doctorSpecs.length; i++) {
                String spec = doctorSpecs[i];
                String docName = doctorNames[i % doctorNames.length];
                providers.add(new Provider(docName, "provider" + (counter++) + "@hospital.com", "password123", "Doctor", hospital, 1L, spec));
                if (!spec.equals("General Medicine")) {
                    providers.add(new Provider(docName + " (Specialist)", "provider" + (counter++) + "@hospital.com", "password123", "Specialist", hospital, 1L, spec));
                }
            }
            for (String nSpec : nurseSpecs) {
                providers.add(new Provider("Sister Kavitha (" + nSpec + ")", "provider" + (counter++) + "@hospital.com", "password123", "Nurse", hospital, 1L, nSpec));
            }
            providers.add(new Provider("Rajesh Kumar (Admin)", "provider" + (counter++) + "@hospital.com", "password123", "Hospital Administrator", hospital, 1L, "Hospital Administration"));
        }

        // 2. Schools (categoryId = 2)
        String[] schools = {"Delhi Public School", "Oakridge International School", "Chirec International School"};
        String[] hodDepts = {"Mathematics", "Science", "Computer Science", "English", "Social Studies", "Commerce", "Languages", "Physical Education"};
        String[] teacherSubjects = {"Mathematics", "Physics", "Chemistry", "Biology", "English", "Telugu", "Hindi", "Social Studies", "Computer Science", "Economics", "Commerce", "History", "Geography", "Physical Education", "Art & Drawing"};

        for (String school : schools) {
            providers.add(new Provider("Dr. Sunita Deshmukh", "provider" + (counter++) + "@school.edu", "password123", "Principal", school, 2L, "Administration"));
            for (String dept : hodDepts) {
                providers.add(new Provider("Prof. Ramesh (" + dept + " HOD)", "provider" + (counter++) + "@school.edu", "password123", "HOD", school, 2L, dept));
            }
            for (String subj : teacherSubjects) {
                providers.add(new Provider("Ms. Divya (" + subj + ")", "provider" + (counter++) + "@school.edu", "password123", "Teacher", school, 2L, subj));
            }
            providers.add(new Provider("Suresh Babu", "provider" + (counter++) + "@school.edu", "password123", "Administrative Staff", school, 2L, "Admissions & Records"));
            providers.add(new Provider("Lakshmi Devi", "provider" + (counter++) + "@school.edu", "password123", "Support Staff", school, 2L, "Student Services"));
        }

        // 3. Government Offices (categoryId = 3)
        String[] govOffices = {"Municipal Corporation Office", "Revenue Department Office", "Regional Transport Office (RTO)"};
        String[] govServices = {"Certificates", "Land / Property", "Licenses", "Applications", "Public Grievance", "Document Verification", "General Enquiry"};
        String[] govRoles = {"Government Officer", "Department Head", "Clerk", "Public Service Staff"};

        for (String office : govOffices) {
            for (String service : govServices) {
                for (String role : govRoles) {
                    providers.add(new Provider(role + " - " + service, "provider" + (counter++) + "@gov.in", "password123", role, office, 3L, service));
                }
            }
        }

        // 4. Business Services (categoryId = 4)
        String[] businesses = {"TechNova Solutions", "Apex Global Consulting", "FinEdge Advisory"};
        String[] businessRoles = {"CEO", "Founder", "Manager", "Business Consultant", "Business Executive", "HR Manager", "Financial Consultant", "Legal Consultant", "Marketing Consultant"};

        for (String biz : businesses) {
            for (String role : businessRoles) {
                providers.add(new Provider("Arjun Kapoor (" + role + ")", "provider" + (counter++) + "@business.com", "password123", role, biz, 4L, role));
            }
        }

        // 5. Public Representatives (categoryId = 5)
        String[] repRoles = {"MLA", "MP", "Municipal Representative", "Public Official"};
        String[] repOffices = {"Central Constituency Office", "North Zone Civic Office", "South Zone Parliamentary Office"};

        for (String role : repRoles) {
            for (String office : repOffices) {
                providers.add(new Provider("Hon. Representative (" + role + ")", "provider" + (counter++) + "@public.org", "password123", role, office, 5L, "Public Grievance"));
            }
        }

        // 6. Entertainment (categoryId = 6)
        String[] entRoles = {"Actor", "Director", "Producer", "Singer", "Artist", "Musician", "Content Creator", "Photographer"};
        String[] entServices = {"Professional Meeting", "Collaboration", "Audition", "Project Discussion", "Consultation", "Interview", "Event Discussion"};

        for (String role : entRoles) {
            for (String service : entServices) {
                providers.add(new Provider("Karan Johar (" + role + ")", "provider" + (counter++) + "@entertainment.com", "password123", role, "Starlight Studios", 6L, service));
            }
        }

        return providerRepository.saveAll(providers);
    }

    private void seedSchedules(List<Provider> providers) {
        List<Schedule> schedules = new ArrayList<>();
        LocalDate tomorrow = LocalDate.now().plusDays(1);
        String[] times = {"09:00 AM", "10:00 AM", "11:00 AM", "02:00 PM", "03:00 PM", "04:00 PM"};

        for (Provider provider : providers) {
            for (int d = 0; d < 5; d++) {
                String dateStr = tomorrow.plusDays(d).toString();
                for (int t = 0; t < 3; t++) {
                    String timeStr = times[(d + t) % times.length];
                    schedules.add(new Schedule(provider.getId(), dateStr, timeStr, true));
                }
            }
        }

        scheduleRepository.saveAll(schedules);
    }
}

