<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<form class="form" action="/leloc-b5.vn/profile" method="post"enctype="multipart/form-data">
	<div class="row">
		<div class="col">
			<div class="row">
				<div class="col">
					<div class="form-group">
						<label>Full Name</label> <input class="form-control" type="text"
							name="fullname" placeholder="${sessionScope.profile.fullname}"
							value="">
					</div>
				</div>
				<div class="col">
					<div class="form-group">
						<label>Username</label> <input class="form-control" type="text"
							name="username" placeholder="${sessionScope.profile.username}"
							value="">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<div class="form-group">
						<label>Email</label> <input class="form-control" type="text"
							name="email" placeholder="${sessionScope.profile.email}">
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-12 col-sm-6 mb-3">
			<div class="mb-2">
				<b>Change Password</b>
			</div>
			<div class="row">
				<div class="col">
					<div class="form-group">
						<label>Password</label> <input class="form-control"
							type="password" name="password" placeholder="••••••">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col">
					<div class="form-group">
						<label>New Password</label> <input class="form-control"
							type="password" name="newpassword" placeholder="••••••">
					</div>
				</div>
			</div>
			
		</div>

	</div>
	Choose a file: <input type="file" name="multiPartServlet"/>
	<div class="row">
		<div class="col d-flex justify-content-end">
			<button class="btn btn-primary" type="submit">Save Changes</button>
		</div>
	</div>
</form>
