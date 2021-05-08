import React, {Component} from 'react';
import {ErrorMessage, Field, Form, Formik} from "formik";
import MatchTestDataService from "../service/MatchTestDataService";
import TournamentDataService from "../service/TournamentDataService";
import axios from "axios";

class ScheduleComponent extends Component{

    constructor(props) {
        super(props);
        this.state = {
            dateTimeStart: '',
            tournamentTitle: '',
            location: '',
            matches: [],
            tournamentType: '',
            errorMessage: null
        }
        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
        this.refreshMatches = this.refreshMatches.bind(this)
        this.deleteMatchClicked = this.deleteMatchClicked.bind(this)
    }

    validate(values) {
        let errors = {}
        if (!values.location) {
            errors.location = 'Enter a location for the event!'
        }
        if (values.tournamentTitle === '') {
            errors.dateTimeStart = 'Enter a date for the event!'
        }
        if (!values.dateTimeStart) {
            errors.dateTimeStart = 'Enter a date for the event!'
        }
        if(!values.tournamentType){
            errors.tournamentType = 'Select a tournament type for the event'
        }
        return errors
    }

    deleteMatchClicked(idMatch) {
        MatchTestDataService.deleteMatch(parseInt(idMatch))
            .then(
                response => {
                    console.log(idMatch)
                    this.refreshMatches()
                }
            )
    }

    onSubmit(values) {
        TournamentDataService.retrieveAllTournaments()
            .then(
                response => {
                    let isUsed = false;
                    let id = -1;
                    for(let tournament of response.data){
                        if(tournament.location === values.location && tournament.dateTimeStart === values.dateTimeStart && tournament.title === values.tournamentTitle ){
                            isUsed = true;
                            id = tournament.idTournament;
                            break;
                        }
                    }
                    let tournament = {
                        idTournament: id,
                        title: values.tournamentTitle,
                        location: values.location,
                        dateTimeStart: values.dateTimeStart,
                        type: values.tournamentType
                    }
                    // eslint-disable-next-line
                    if(isUsed == true){
                        MatchTestDataService.createMatch(tournament)
                            .then(this.refreshMatches)
                            .catch(
                                error => {
                                    this.setState({errorMessage: error.response.data})
                                    console.log(this.state.errorMessage)
                                }
                            )
                    }
                    else{
                        axios.put("http://localhost:8080/mma/data/week-reset")
                        TournamentDataService.createTournament2(tournament)
                            .then(
                                response => {
                                    MatchTestDataService.createMatch(response.data)
                                        .then(this.refreshMatches)
                                        .catch(
                                            error => {
                                                this.setState({errorMessage: error.response.data})
                                                console.log(this.state.errorMessage)
                                            }
                                        )
                                }
                            )
                    }
                }
            )

    }

    refreshMatches(){
        MatchTestDataService.retrieveAllMatches()
            .then(
                response => {
                    this.setState({ matches: response.data})
                }
            )
    }

    render() {
        const {tournamentTitle,dateTimeStart, location, tournamentType} = this.state
        return(
            <div className='container'>
                {this.state.errorMessage && <div className="alert alert-danger">{this.state.errorMessage}</div>}
                <Formik
                    initialValues={{ dateTimeStart,tournamentTitle, location, tournamentType}}
                    onSubmit={this.onSubmit}
                    validateOnChange={false}
                    validateOnBlur={false}
                    validate={this.validate}
                    enableReinitialize={true}
                >
                    {
                        (props) => (
                            <Form>
                                <ErrorMessage name="tournamentTitle" component="div"
                                              className="alert alert-warning" />
                                <ErrorMessage name="location" component="div"
                                              className="alert alert-warning" />
                                <ErrorMessage name="dateTimeStart" component="div"
                                              className="alert alert-warning" />
                                <ErrorMessage name="tournamentType" component="div"
                                              className="alert alert-warning" />
                                <fieldset className="form-group">
                                    <label>Enter the title of the tournament</label>
                                    <Field className="form-control" type="text" name="tournamentTitle"/>
                                </fieldset>
                                <fieldset className="form-group">
                                    <label>Enter the location of the tournament</label>
                                    <Field className="form-control" type="text" name="location"/>
                                </fieldset>
                                <fieldset className="form-group">
                                    <label>Enter the beginning date of the tournament</label>
                                    <Field className="form-control" type="date" name="dateTimeStart"/>
                                </fieldset>
                                <fieldset className="form-group">
                                    <label>Select the occurrence rate of the matches</label>
                                    <div role="group" aria-labelledby="my-radio-group">
                                        <label>
                                            <Field type="radio" name="tournamentType" value="Weekly" />
                                            Weekly
                                        </label>
                                        <label>
                                            <Field type="radio" name="tournamentType" value="Monthly" />
                                            Monthly
                                        </label>
                                    </div>
                                </fieldset>
                                <button className="btn btn-success" type="submit">Schedule</button>
                            </Form>
                        )
                    }
                </Formik>
                <br></br><br></br>
                <div className="table">
                    <thead>
                        <tr>
                            <th>Id Match</th>
                            <th>Tournament Title</th>
                            <th>Fighter 1</th>
                            <th>Fighter 2</th>
                            <th>Winner</th>
                            <th>Rounds</th>
                            <th>Date</th>
                            <th>Delete</th>
                        </tr>
                    </thead>
                    <tbody>
                    {
                        this.state.matches.map(
                            match =>
                                <tr key={match.id}>
                                    <td>{match.idMatch}</td>
                                    <td>{match.tournamentTitle}</td>
                                    <td>{match.nameFighter1}</td>
                                    <td>{match.nameFighter2}</td>
                                    <td>{match.winner}</td>
                                    <td>{match.rounds}</td>
                                    <td>{match.dateTimeStart}</td>
                                    <td><button className="btn btn-warning" onClick={() => this.deleteMatchClicked(match.idMatch)}>Delete</button></td>
                                </tr>
                        )
                    }
                    </tbody>
                </div>
                <button className="btn btn-success" onClick={this.refreshMatches}>Refresh matches</button>
            </div>
        )
    }
}

export default ScheduleComponent