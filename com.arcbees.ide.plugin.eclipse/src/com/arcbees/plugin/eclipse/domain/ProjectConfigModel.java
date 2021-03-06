/**
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.plugin.eclipse.domain;


public class ProjectConfigModel extends ModelObject {
    private String projectName;
    private String packageName;
    private String moduleName;
    private String groupId;
    private String artifactId;
    private String projectPath;
    private Archetype archetypeSelected;
    private String workspacePath;
    
    public ProjectConfigModel() {
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        firePropertyChange("packageName", this.packageName, this.packageName = packageName);
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        firePropertyChange("moduleName", this.moduleName, this.moduleName = moduleName);
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        firePropertyChange("groupId", this.groupId, this.groupId = groupId);
    }

    public String getArtifactId() {
        return artifactId;
    }

    public void setArtifactId(String artifactId) {
        firePropertyChange("artifactId", this.artifactId, this.artifactId = artifactId);
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        firePropertyChange("projectPath", this.projectPath, this.projectPath = projectPath);
    }
    
    public Archetype getArchetypeSelected() {
        return archetypeSelected;
    }

    public void seArchetypeSelected(Archetype archetypeSelected) {
        firePropertyChange("archetypeSelected", this.archetypeSelected, this.archetypeSelected = archetypeSelected);
    }
    
    @Override
    public String toString() {
        String s = "{ ProjectConfigModel: ";
        s += "projectName=" + projectName + " ";
        s += "packageName=" + packageName + " ";
        s += "moduleName=" + moduleName + " ";
        s += "groupId=" + groupId + " ";
        s += "artifactId=" + artifactId + " ";
        s += "projectPath=" + projectPath + " ";
        s += "archetypeSelected=" + archetypeSelected + " ";
        s += " }"; 
        return s;
    }

    // TODO future
    public String getVersion() {
        return "1.0-SNAPSHOT";
    }

    // TODO future
    public String getDescription() {
        return "This project was genereted by ArcBees Eclipse plugin.";
    }

    public boolean canBeFinished() {
        return archetypeSelected != null;
    }

    public void setWorkspacePath(String workspacePath) {
        this.workspacePath = workspacePath;
    }
    
    public String getWorkspacePath() {
        return workspacePath;
    }
}
