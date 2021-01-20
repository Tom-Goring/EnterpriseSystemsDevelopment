/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Models.PrescriptionsApproval;

import Models.Prescription.Prescription;

/**
 *
 * @author maxwell
 */
public class PrescriptionsApproval {
    
    final Integer ID;
    final Prescription prescription;
    boolean actioned;

    public PrescriptionsApproval(Integer ID, Prescription prescription, boolean actioned) {
        this.ID = ID;
        this.prescription = prescription;
        this.actioned = actioned;
    }

    public Integer getID() {
        return ID;
    }

    public Prescription getPrescription() {
        return prescription;
    }

    public boolean isActioned() {
        return actioned;
    }

    public void setActioned(boolean actioned) {
        this.actioned = actioned;
    }

    @Override
    public String toString() {
        return "PrescriptionsApproval{" + "ID=" + ID + ", prescription=" + prescription + ", actioned=" + actioned + '}';
    }
    
}
    

