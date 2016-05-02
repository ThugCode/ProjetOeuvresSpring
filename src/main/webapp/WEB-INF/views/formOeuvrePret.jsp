<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:layout>
    <jsp:body>
		
		<t:form classe="OeuvrePret" 
    				titre="${action} une oeuvre prêt" 
    				action="${action}">
			<jsp:body>
		
				<input type="hidden" name="idOeuvre" class="form-control" id="idOeuvre" value="${oeuvrePret.idOeuvrepret}">
				
				<div class="form-group">
					<label for="titreOeuvre" class="col-sm-2 control-label">Titre</label>
					<div class="col-sm-10">
						<input type="text" name="txtTitre" class="form-control"
							id="titreOeuvre" placeholder="Titre" value="${oeuvrePret.titreOeuvrepret}">
					</div>
				</div>
				<div class="form-group">
					<label for="proprietaire" class="col-sm-2 control-label">Proprietaire</label>
					<div class="col-sm-10">
						<select class="form-control" name="txtProprietaire" id="idProprietaire">
						<c:forEach items="${proprietaires}" var="item">
							<option value="${item.idProprietaire}" ${item.idProprietaire == oeuvrePret.proprietaire.idProprietaire ? 'selected' : '' }>
								${item.nomProprietaire} ${item.prenomProprietaire}
							</option>
						</c:forEach>
						</select>
					</div>
				</div>
			
			</jsp:body>
		</t:form>
	
    </jsp:body>
</t:layout>

