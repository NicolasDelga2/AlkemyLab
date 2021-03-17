const d = document,
    $editButtons = d.querySelectorAll('.editButton'),
    $editModal = d.querySelector('#editModal'),
    $detailsModal = d.querySelector('#detailsModal'),
    $detailsButtons = d.querySelectorAll('.detailsButton'),
    $deleteModal = d.querySelector('#deleteModal'),
    $deleteButtons = d.querySelectorAll('.deleteButton');

/* Edit Modal */
$editButtons.forEach(($editButton) => {
    $editButton.addEventListener("click", (e) => {
        e.preventDefault();
        let url = location.origin;
        let link = e.currentTarget.getAttribute('href');
        getEdit(url,link);
    });
});

const getEdit = async (url,link) => {
    try {
        let res = await fetch(`${url}${link}`);
        let json = await res.json();
        if (!res.ok) throw {
            status: res.status,
            statusText: res.statusText
        };
        $editModal.querySelector('#idEdit').value = json.id;
        $editModal.querySelector('#nameEdit').value = json.name;
        $editModal.querySelector('#lastnameEdit').value = json.lastname;
        $editModal.querySelector('#dniEdit').value = json.dni;
        $editModal.querySelector('#isActiveEdit').value = json.isActive;

    } catch (err) {
        // Set the error
    }
    $editModal.classList.add('show')
    $editModal.style = 'display:block';
}

$editModal.querySelector('.close').addEventListener('click', (e) => {
    $editModal.classList.remove('show');
    $editModal.style = 'display:none';
})

/* Details Modal */
$detailsButtons.forEach(($detailButton) => {
    $detailButton.addEventListener('click', e => {
        e.preventDefault();
        let url = location.origin;
        let link = e.currentTarget.getAttribute('href');
        getTeacher(url,link);
    })
})

const getTeacher = async (url,link) => {
    try {
        let res = await fetch(`${url}${link}`)
        let json = await res.json();

        $detailsModal.querySelector('#nameDetails').value = json.name;
        $detailsModal.querySelector('#lastnameDetails').value = json.lastname;
        $detailsModal.querySelector('#dniDetails').value = json.dni;
        $detailsModal.querySelector('#isActiveDetails').value = json.isActive;

    }catch(err){

    }
    $detailsModal.classList.add('show')
    $detailsModal.style = 'display:block';
}

$detailsModal.querySelector('.close').addEventListener('click', (e) => {
    $detailsModal.classList.remove('show');
    $detailsModal.style = 'display:none';
})

/* Delete Modal */
$deleteButtons.forEach(($deleteButton) => {
    $deleteButton.addEventListener('click', e => {
        e.preventDefault();
        let url = location.origin;
        let link = e.currentTarget.getAttribute('href');
        $deleteModal.querySelector('#confirmDeleteButton')
            .setAttribute('href',`${url}${link}`);
        $deleteModal.classList.add('show');
        $deleteModal.style = 'display:block';
    })
})

$deleteModal.addEventListener('click', e => {
    if(e.target.matches('.close') || e.target.matches('.cancel-button')){
        $deleteModal.classList.remove('show');
        $deleteModal.style = 'display:none';
    }
})