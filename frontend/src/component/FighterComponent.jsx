import React, { Component } from 'react';
import { Formik, Form, Field, ErrorMessage } from 'formik';
import FighterDataService from "../service/FighterDataService";

class FighterComponent extends Component{

    constructor(props) {
        super(props)

        this.state = {
            idFighter: this.props.match.params.idFighter,
            firstname: '',
            lastname: '',
            weight: '',
            initialTestId: '',
            secondTestId: '',
            inQuarantine: '',
            color: '',
            errorMessage: null
        }
        this.onSubmit = this.onSubmit.bind(this)
        this.validate = this.validate.bind(this)
    }

    onSubmit(values) {
        let fighter = {
            idFighter: parseInt(this.state.idFighter),
            firstname: values.firstname,
            lastname: values.lastname,
            weight: parseInt(values.weight),
            initialTestId: parseInt(values.initialTestId),
            secondTestId: parseInt(values.secondTestId),
            inQuarantine: values.inQuarantine === 'true',
            color: values.color
        }
        // eslint-disable-next-line
        if(this.state.idFighter == -1){
            FighterDataService.createFighter(fighter)
                // .then(() => this.props.history.push('/administrator/fighters'))
                .catch(
                    error => {
                        this.setState({errorMessage: error.response.data})
                    }
                )
        }else{
            FighterDataService.updateFighter(this.state.idFighter, fighter)
                .then(() => this.props.history.push('/administrator/fighters'))
        }
    }

    componentDidMount() {
        // eslint-disable-next-line
        if (this.state.idFighter == -1) {
            return;
        }
        FighterDataService.retrieveFighter(this.state.idFighter)
            .then(
                response =>
                    this.setState({
                        firstname: response.data.firstname,
                        lastname: response.data.lastname,
                        weight: response.data.weight,
                        initialTestId: response.data.initialTestId,
                        secondTestId: response.data.secondTestId,
                        inQuarantine: response.data.inQuarantine,
                        color: response.data.color
                    }));
    }

    validate(values) {
        let errors = {}
        if (!values.firstname) {
            errors.firstname = 'Enter a firstname'
        }
        else if (/[0-9]/.test(values.firstname)){
            errors.weight = 'Digits can not be added into the First Name field'
        }
        if (!values.lastname) {
            errors.lastname = 'Enter a lastname'
        }
        else if (/[0-9]/.test(values.lastname)){
            errors.lastname = 'Digits can not be added into the Last Name field'
        }
        if (!values.weight) {
            errors.weight = 'Enter a weight'
        }
        else if (/[^0-9]/.test(values.weight)){
            errors.weight = 'Letters can not be added into the Weight field'
        }
        if (values.initialTestId === '') {
            errors.initialTestId = 'Enter a Initial Test Id'
        }
        else if (/[^0-9]/.test(values.initialTestId)){
            errors.initialTestId = 'Letters can not be added into the Initial Test Id field'
        }
        if (values.secondTestId === '') {
            errors.secondTestId = 'Enter a Second Test Id'
        }
        else if (/[^0-9]/.test(values.secondTestId)){
            errors.secondTestId = 'Letters can not be added into the Second Test Id field'
        }
        if (values.inQuarantine === '') {
            errors.inQuarantine = 'Enter a value for the Quarantined field'
        }
        else if(values.inQuarantine !== "true" && values.inQuarantine !== "false"){
            errors.inQuarantine = 'Enter a valid value for the Quarantined field\nType true or false'
        }
        if (values.color === '') {
            errors.color = 'Enter a color'
        }
        else if (values.color !== 'grey' && values.color !== 'green' && values.color !== 'red'){
            errors.color = 'Color field can be either green, red or grey'
        }

        return errors
    }

    render() {
        let { idFighter, firstname, lastname, weight, initialTestId, secondTestId, inQuarantine, color } = this.state
        return (
            <div>
                <h3>Fighter</h3>
                {this.state.errorMessage && <div className="alert alert-danger">{this.state.errorMessage}</div>}
                <div className="container">
                    <Formik
                        initialValues={{ idFighter, firstname, lastname, weight, initialTestId, secondTestId, inQuarantine, color }}
                        onSubmit={this.onSubmit}
                        validateOnChange={false}as
                        validateOnBlur={false}
                        validate={this.validate}
                        enableReinitialize={true}
                    >
                        {
                            (props) => (
                                <Form>
                                    <ErrorMessage name="firstname" component="div"
                                                  className="alert alert-warning" />
                                    <ErrorMessage name="lastname" component="div"
                                                  className="alert alert-warning" />
                                    <ErrorMessage name="weight" component="div"
                                                  className="alert alert-warning" />
                                    <ErrorMessage name="initialTestId" component="div"
                                                  className="alert alert-warning" />
                                    <ErrorMessage name="secondTestId" component="div"
                                                  className="alert alert-warning" />
                                    <ErrorMessage name="inQuarantine" component="div"
                                                  className="alert alert-warning" />
                                    <ErrorMessage name="color" component="div"
                                                  className="alert alert-warning" />
                                    <fieldset className="form-group">
                                        <label>Id</label>
                                        <Field className="form-control" type="text" name="idFighter" disabled/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>First Name</label>
                                        <Field className="form-control" type="text" name="firstname"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Last Name</label>
                                        <Field className="form-control" type="text" name="lastname"/>
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Weight</label>
                                        <Field className="form-control" type="text" name="weight" />
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Initial Test Id</label>
                                        <Field className="form-control" type="text" name="initialTestId"  />
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Second Test Id</label>
                                        <Field className="form-control" type="text" name="secondTestId"  />
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Quarantined</label>
                                        <Field className="form-control" type="text" name="inQuarantine"  />
                                    </fieldset>
                                    <fieldset className="form-group">
                                        <label>Color</label>
                                        <Field className="form-control" type="text" name="color"  />
                                    </fieldset>
                                    <button className="btn btn-success" type="submit">Save</button>
                                </Form>
                            )
                        }
                    </Formik>

                </div>
            </div>
        )
    }
}

export default FighterComponent