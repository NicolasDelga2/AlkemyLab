const d = document,
    $detailsModal = d.querySelector('#detailsModal'),
    $detailsButtons = d.querySelectorAll('.detailsButton'),
    $addSubjectStudentButtons = d.querySelectorAll('.addStudentToSubject'),
    $subjectAddModal = d.querySelector('#addModal'),
    $removeSubjectButton = d.querySelectorAll('.removeSubjectButton'),
    $removeSubjectModal = d.querySelector('#removeSubjectModal'),
    $sidebar = document.querySelector('#accordionSidebar');

/* Details Modal */
$detailsButtons.forEach(($detailButton) => {
    $detailButton.addEventListener('click', e => {
        e.preventDefault();
        let link = e.currentTarget.getAttribute('href');
        getSubjects(link);
    })
})

const getSubjects = async (link) => {
    try {
        let res = await fetch(`http://localhost:8080${link}`)
        let json = await res.json();

        $detailsModal.querySelector('#nameDetails').value = json.name;
        $detailsModal.querySelector('#startTimeDetails').value = json.startTime;
        $detailsModal.querySelector('#finishTimeDetails').value = json.finishTime;
        $detailsModal.querySelector('#vacancyDetails').value = json.inscriptionVacancy;

    } catch (err) {
    }
    $detailsModal.classList.add('show')
    $detailsModal.style = 'display:block';
}
$detailsModal.addEventListener('click', (e) => {
    if (e.target.matches(".close *") || e.target.matches(".close-button")) {
        $detailsModal.classList.remove('show');
        $detailsModal.style = 'display:none';
    }
})


// Add Student to a subject
$addSubjectStudentButtons.forEach(($addSubject) => {
    $addSubject.addEventListener('click', e => {
        e.preventDefault();
        let link = e.currentTarget.getAttribute('href');
        $subjectAddModal.querySelector('#confirmAddButton')
            .setAttribute('href', `http://localhost:8080${link}`);
        $subjectAddModal.classList.add('show');
        $subjectAddModal.style = 'display:block';
    })
})


$subjectAddModal.addEventListener('click', e => {
    if (e.target.matches('.close *') || e.target.matches('.cancel-button')) {
        $subjectAddModal.classList.remove('show');
        $subjectAddModal.style = 'display:none';
    }
});

// Toggle Sidebar
document.addEventListener('click', e => {
    if (e.target.matches('#sidebarToggleTop *') || e.target.matches('#sidebarToggle')) {
        $sidebar.classList.toggle('toggled');
    }
});

// Remove Subject
$removeSubjectButton.forEach(($removeButton) => {
    $removeButton.addEventListener('click', e => {
        e.preventDefault();
        let link = e.currentTarget.getAttribute('href');
        $removeSubjectModal.querySelector('#confirmAddButton')
            .setAttribute('href', `http://localhost:8080${link}`);
        $removeSubjectModal.classList.add('show');
        $removeSubjectModal.style = 'display:block';
    })
})


$removeSubjectModal.addEventListener('click', e => {
    if (e.target.matches('.close *') || e.target.matches('.cancel-button')) {
        $removeSubjectModal.classList.remove('show');
        $removeSubjectModal.style = 'display:none';
    }
});